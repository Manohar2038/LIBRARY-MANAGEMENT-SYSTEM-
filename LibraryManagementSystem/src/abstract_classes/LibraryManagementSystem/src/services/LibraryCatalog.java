package services;

import exceptions.*;
import models.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Central catalog managing all books and members.
 * ENCAPSULATION: Internal lists are private, accessed only through methods.
 */
public class LibraryCatalog {

    private List<Book> books;
    private List<Member> members;
    private SortService sortService;
    private SearchService searchService;
    private TransactionService transactionService;

    public LibraryCatalog() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.sortService = new SortService();
        this.searchService = new SearchService();
        this.transactionService = new TransactionService();
    }

    // ==================== BOOK MANAGEMENT ====================

    public void addBook(Book book) {
        books.add(book);
        // Keep sorted by ISBN for binary search
        books = sortService.mergeSort(books, "isbn");
        System.out.println("  ✓ Book added: " + book.getTitle() + " [" + book.getIsbn() + "]");
    }

    public void removeBook(String isbn) throws BookNotFoundException {
        Book book = searchService.binarySearchByISBN(books, isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        books.remove(book);
        System.out.println("  ✓ Book removed: " + book.getTitle());
    }

    public Book findBookByISBN(String isbn) throws BookNotFoundException {
        Book book = searchService.binarySearchByISBN(books, isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        return book;
    }

    public Book findBookByTitle(String title) {
        List<Book> sortedByTitle = sortService.mergeSort(new ArrayList<>(books), "title");
        return searchService.binarySearchByTitle(sortedByTitle, title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return searchService.searchByAuthor(books, author);
    }

    public List<Book> findBooksByGenre(String genre) {
        return searchService.searchByGenre(books, genre);
    }

    // ==================== MEMBER MANAGEMENT ====================

    public void addMember(Member member) {
        members.add(member);
        System.out.println("  ✓ Member added: " + member.getName() +
                           " [" + member.getMemberId() + "]");
    }

    public void removeMember(String memberId) throws MemberNotFoundException {
        Member member = findMemberById(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }
        members.remove(member);
        System.out.println("  ✓ Member removed: " + member.getName());
    }

    public Member findMemberById(String memberId) throws MemberNotFoundException {
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }
        throw new MemberNotFoundException(memberId);
    }

    // ==================== TRANSACTION OPERATIONS ====================

    public Transaction issueBook(String isbn, String memberId)
            throws BookNotFoundException, BookAlreadyIssuedException,
                   MaxBooksExceededException, MemberNotFoundException {
        Book book = findBookByISBN(isbn);
        Member member = findMemberById(memberId);
        return transactionService.issueBook(book, member);
    }

    public Transaction returnBook(String isbn, String memberId)
            throws BookNotFoundException, InvalidReturnException,
                   MemberNotFoundException {
        Book book = findBookByISBN(isbn);
        Member member = findMemberById(memberId);
        return transactionService.returnBook(book, member);
    }

    // ==================== DISPLAY / SORT OPERATIONS ====================

    public List<Book> getBooksSortedBy(String criteria, String algorithm) {
        if (algorithm.equalsIgnoreCase("merge")) {
            return sortService.mergeSort(new ArrayList<>(books), criteria);
        } else {
            List<Book> booksCopy = new ArrayList<>(books);
            return sortService.quickSort(booksCopy, 0, booksCopy.size() - 1, criteria);
        }
    }

    public void displayAllBooks() {
        System.out.println("\n╔══════════════════════════════════════════════" +
                           "════════════════════════════════════════════════╗");
        System.out.println("║                              LIBRARY CATALOG" +
                           "                                              ║");
        System.out.println("╠══════════════════════════════════════════════" +
                           "════════════════════════════════════════════════╣");
        System.out.printf("║ %-13s | %-30s | %-20s | %-12s | %-4s | %-15s ║\n",
                          "ISBN", "TITLE", "AUTHOR", "GENRE", "YEAR", "AVAILABILITY");
        System.out.println("╠══════════════════════════════════════════════" +
                           "════════════════════════════════════════════════╣");
        for (Book book : books) {
            System.out.println("║" + book.getDisplayInfo() + "║");
        }
        System.out.println("╚══════════════════════════════════════════════" +
                           "════════════════════════════════════════════════╝");
        System.out.println("  Total Books: " + books.size());
    }

    public void displayAllMembers() {
        System.out.println("\n╔══════════════════════════════════════════════" +
                           "══════════════════════════════════════════════════════╗");
        System.out.println("║                                LIBRARY MEMBERS" +
                           "                                                    ║");
        System.out.println("╠══════════════════════════════════════════════" +
                           "══════════════════════════════════════════════════════╣");
        for (Member member : members) {
            System.out.println("║ " + member.getDisplayInfo() + " ║");
        }
        System.out.println("╚══════════════════════════════════════════════" +
                           "══════════════════════════════════════════════════════╝");
        System.out.println("  Total Members: " + members.size());
    }

    public void displayTransactionHistory() {
        List<Transaction> history = transactionService.getTransactionHistory();
        System.out.println("\n═══════════════════ TRANSACTION HISTORY ═══════════════════");
        if (history.isEmpty()) {
            System.out.println("  No transactions recorded yet.");
        } else {
            for (Transaction txn : history) {
                System.out.println(txn);
            }
        }
        System.out.println("═══════════════════════════════════════════════════════════");
    }

    // Getters for services
    public SortService getSortService() { return sortService; }
    public SearchService getSearchService() { return searchService; }
    public TransactionService getTransactionService() { return transactionService; }
    public List<Book> getBooks() { return new ArrayList<>(books); }
    public List<Member> getMembers() { return new ArrayList<>(members); }
}