import exceptions.*;
import models.*;
import services.*;
import utils.DateUtils;
import java.util.List;
import java.util.Scanner;

/**
 * LIBRARY MANAGEMENT SYSTEM — Console Application
 * 
 * Demonstrates all four OOP pillars:
 *   1. INHERITANCE:    Book → EBook, PrintedBook | Member → StudentMember, FacultyMember
 *   2. ENCAPSULATION:  Private fields, public getters/setters, defensive copies
 *   3. POLYMORPHISM:   Overridden getDisplayInfo(), getEntityType(); interface implementations
 *   4. ABSTRACTION:    Abstract class LibraryEntity, TransactionProcessor; Interfaces
 * 
 * DSA concepts:
 *   - Binary Search O(log n) for book lookups by ISBN and Title
 *   - Merge Sort O(n log n) for stable catalog sorting
 *   - Quick Sort O(n log n) avg for in-place catalog sorting
 *   - Recursion for fine calculations
 */
public class Main {

    private static LibraryCatalog catalog = new LibraryCatalog();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║        LIBRARY MANAGEMENT SYSTEM v1.0               ║");
        System.out.println("║        Java · OOP · DSA · Sorting · Binary Search   ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Load sample data
        loadSampleData();

        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("\n  Enter your choice: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":  addBook(); break;
                    case "2":  addMember(); break;
                    case "3":  issueBook(); break;
                    case "4":  returnBook(); break;
                    case "5":  searchBookByISBN(); break;
                    case "6":  searchBookByTitle(); break;
                    case "7":  searchBooksByAuthor(); break;
                    case "8":  searchBooksByGenre(); break;
                    case "9":  displaySortedCatalog(); break;
                    case "10": catalog.displayAllBooks(); break;
                    case "11": catalog.displayAllMembers(); break;
                    case "12": catalog.displayTransactionHistory(); break;
                    case "13": demonstrateSorting(); break;
                    case "14": demonstrateBinarySearch(); break;
                    case "15": demonstrateFineCalculation(); break;
                    case "16": demonstratePolymorphism(); break;
                    case "0":
                        running = false;
                        System.out.println("\n  Thank you for using Library Management System!");
                        System.out.println("  Goodbye!\n");
                        break;
                    default:
                        System.out.println("  ✗ Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("  ✗ Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // ==================== MENU ====================

    private static void displayMenu() {
        System.out.println("\n┌──────────────────────────────────────────────┐");
        System.out.println("│              MAIN MENU                       │");
        System.out.println("├──────────────────────────────────────────────┤");
        System.out.println("│  1.  Add Book                                │");
        System.out.println("│  2.  Add Member                              │");
        System.out.println("│  3.  Issue Book                              │");
        System.out.println("│  4.  Return Book                             │");
        System.out.println("│  5.  Search Book by ISBN (Binary Search)     │");
        System.out.println("│  6.  Search Book by Title (Binary Search)    │");
        System.out.println("│  7.  Search Books by Author                  │");
        System.out.println("│  8.  Search Books by Genre                   │");
        System.out.println("│  9.  Display Sorted Catalog                  │");
        System.out.println("│  10. Display All Books                       │");
        System.out.println("│  11. Display All Members                     │");
        System.out.println("│  12. Display Transaction History             │");
        System.out.println("│  ── DEMONSTRATIONS ──                        │");
        System.out.println("│  13. Demonstrate Sorting (Merge vs Quick)    │");
        System.out.println("│  14. Demonstrate Binary Search               │");
        System.out.println("│  15. Demonstrate Recursive Fine Calculation  │");
        System.out.println("│  16. Demonstrate Polymorphism                │");
        System.out.println("│  0.  Exit                                    │");
        System.out.println("└──────────────────────────────────────────────┘");
    }

    // ==================== LOAD SAMPLE DATA ====================

    private static void loadSampleData() {
        System.out.println("\n  Loading sample data...\n");

        // Add Printed Books (INHERITANCE: PrintedBook extends Book)
        catalog.addBook(new PrintedBook("978-0134685991", "Effective Java",
            "Joshua Bloch", "Programming", 2018, 3, 416, "A-101", "NEW"));
        catalog.addBook(new PrintedBook("978-0596009205", "Head First Design Patterns",
            "Eric Freeman", "Programming", 2004, 2, 694, "A-102", "GOOD"));
        catalog.addBook(new PrintedBook("978-0201633610", "Design Patterns",
            "Gang of Four", "Programming", 1994, 2, 395, "A-103", "FAIR"));
        catalog.addBook(new PrintedBook("978-0131872486", "Thinking in Java",
            "Bruce Eckel", "Programming", 2006, 3, 1520, "A-104", "GOOD"));
        catalog.addBook(new PrintedBook("978-0321125217", "Domain-Driven Design",
            "Eric Evans", "Software Engineering", 2003, 2, 560, "B-201", "NEW"));

        // Add EBooks (INHERITANCE: EBook extends Book)
        catalog.addBook(new EBook("978-0132350884", "Clean Code",
            "Robert Martin", "Programming", 2008, 5,
            "PDF", 4.5, "https://library.com/clean-code"));
        catalog.addBook(new EBook("978-0137081073", "The Clean Coder",
            "Robert Martin", "Programming", 2011, 3,
            "EPUB", 2.8, "https://library.com/clean-coder"));
        catalog.addBook(new EBook("978-0984782857", "Cracking the Coding Interview",
            "Gayle McDowell", "Interview Prep", 2015, 4,
            "PDF", 8.2, "https://library.com/ctci"));

        // Add more books for better sort/search demonstration
        catalog.addBook(new PrintedBook("978-0062316097", "Sapiens",
            "Yuval Noah Harari", "History", 2015, 3, 464, "C-301", "NEW"));
        catalog.addBook(new PrintedBook("978-0142437230", "Don Quixote",
            "Miguel de Cervantes", "Classic Fiction", 1605, 2, 1072, "D-401", "FAIR"));
        catalog.addBook(new EBook("978-0061120084", "To Kill a Mockingbird",
            "Harper Lee", "Classic Fiction", 1960, 4,
            "MOBI", 1.2, "https://library.com/tkam"));
        catalog.addBook(new PrintedBook("978-0451524935", "1984",
            "George Orwell", "Dystopian Fiction", 1949, 3, 328, "D-402", "GOOD"));

        System.out.println();

        // Add Members (INHERITANCE + POLYMORPHISM)
        // StudentMember extends Member (max 3 books)
        catalog.addMember(new StudentMember("STU-001", "Alice Johnson",
            "alice@university.edu", "555-0101", "S12345", "Computer Science", 2026));
        catalog.addMember(new StudentMember("STU-002", "Bob Smith",
            "bob@university.edu", "555-0102", "S12346", "Mathematics", 2025));
        catalog.addMember(new StudentMember("STU-003", "Charlie Brown",
            "charlie@university.edu", "555-0103", "S12347", "Physics", 2027));

        // FacultyMember extends Member (max 10 books)
        catalog.addMember(new FacultyMember("FAC-001", "Dr. Sarah Williams",
            "sarah@university.edu", "555-0201", "E5001", "Computer Science", "Professor"));
        catalog.addMember(new FacultyMember("FAC-002", "Dr. James Davis",
            "james@university.edu", "555-0202", "E5002", "Literature", "Associate Professor"));

        System.out.println("\n  ✓ Sample data loaded successfully!");
    }

    // ==================== BOOK OPERATIONS ====================

    private static void addBook() {
        System.out.println("\n  ── ADD NEW BOOK ──");
        System.out.print("  Type (1=Printed, 2=EBook): ");
        String type = scanner.nextLine().trim();

        System.out.print("  ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("  Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("  Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("  Genre: ");
        String genre = scanner.nextLine().trim();
        System.out.print("  Publication Year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("  Total Copies: ");
        int copies = Integer.parseInt(scanner.nextLine().trim());

        if (type.equals("1")) {
            System.out.print("  Number of Pages: ");
            int pages = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("  Shelf Location: ");
            String shelf = scanner.nextLine().trim();
            System.out.print("  Condition (NEW/GOOD/FAIR/POOR): ");
            String condition = scanner.nextLine().trim();

            catalog.addBook(new PrintedBook(isbn, title, author, genre,
                           year, copies, pages, shelf, condition));
        } else {
            System.out.print("  File Format (PDF/EPUB/MOBI): ");
            String format = scanner.nextLine().trim();
            System.out.print("  File Size (MB): ");
            double size = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("  Download Link: ");
            String link = scanner.nextLine().trim();

            catalog.addBook(new EBook(isbn, title, author, genre,
                           year, copies, format, size, link));
        }
    }

    // ==================== MEMBER OPERATIONS ====================

    private static void addMember() {
        System.out.println("\n  ── ADD NEW MEMBER ──");
        System.out.print("  Type (1=Student, 2=Faculty): ");
        String type = scanner.nextLine().trim();

        System.out.print("  Member ID: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("  Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("  Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("  Phone: ");
        String phone = scanner.nextLine().trim();

        if (type.equals("1")) {
            System.out.print("  Student ID: ");
            String studentId = scanner.nextLine().trim();
            System.out.print("  Department: ");
            String dept = scanner.nextLine().trim();
            System.out.print("  Graduation Year: ");
            int gradYear = Integer.parseInt(scanner.nextLine().trim());

            catalog.addMember(new StudentMember(memberId, name, email, phone,
                             studentId, dept, gradYear));
        } else {
            System.out.print("  Employee ID: ");
            String empId = scanner.nextLine().trim();
            System.out.print("  Department: ");
            String dept = scanner.nextLine().trim();
            System.out.print("  Designation: ");
            String designation = scanner.nextLine().trim();

            catalog.addMember(new FacultyMember(memberId, name, email, phone,
                             empId, dept, designation));
        }
    }

    // ==================== ISSUE & RETURN ====================

    private static void issueBook() {
        System.out.println("\n  ── ISSUE BOOK ──");
        System.out.print("  Enter Book ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("  Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        try {
            Transaction txn = catalog.issueBook(isbn, memberId);
            System.out.println("\n  ✓ BOOK ISSUED SUCCESSFULLY!");
            System.out.println("  Transaction: " + txn);
        } catch (BookNotFoundException e) {
            System.out.println("  ✗ " + e.getMessage());
        } catch (BookAlreadyIssuedException e) {
            System.out.println("  ✗ " + e.getMessage());
        } catch (MaxBooksExceededException e) {
            System.out.println("  ✗ " + e.getMessage());
        } catch (MemberNotFoundException e) {
            System.out.println("  ✗ " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.println("\n  ── RETURN BOOK ──");
        System.out.print("  Enter Book ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("  Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        try {
            Transaction txn = catalog.returnBook(isbn, memberId);
            System.out.println("\n  ✓ BOOK RETURNED SUCCESSFULLY!");
            System.out.println("  Transaction: " + txn);
            if (txn.getFineAmount() > 0) {
                System.out.printf("  ⚠ Fine of $%.2f has been applied.\n",
                                  txn.getFineAmount());
            }
        } catch (BookNotFoundException e) {
            System.out.println("  ✗ " + e.getMessage());
        } catch (InvalidReturnException e) {
            System.out.println("  ✗ " + e.getMessage());
        } catch (MemberNotFoundException e) {
            System.out.println("  ✗ " + e.getMessage());
        }
    }

    // ==================== SEARCH OPERATIONS ====================

    private static void searchBookByISBN() {
        System.out.println("\n  ── BINARY SEARCH BY ISBN ──");
        System.out.print("  Enter ISBN: ");
        String isbn = scanner.nextLine().trim();

        try {
            long start = System.nanoTime();
            Book book = catalog.findBookByISBN(isbn);
            long elapsed = System.nanoTime() - start;

            System.out.println("\n  ✓ FOUND! (Binary Search Time: " + elapsed + " ns)");
            System.out.println("  " + book.getDisplayInfo());
            System.out.println("  Type: " + book.getEntityType());
        } catch (BookNotFoundException e) {
            System.out.println("  ✗ " + e.getMessage());
        }
    }

    private static void searchBookByTitle() {
        System.out.println("\n  ── BINARY SEARCH BY TITLE ──");
        System.out.print("  Enter exact title: ");
        String title = scanner.nextLine().trim();

        long start = System.nanoTime();
        Book book = catalog.findBookByTitle(title);
        long elapsed = System.nanoTime() - start;

        if (book != null) {
            System.out.println("\n  ✓ FOUND! (Binary Search Time: " + elapsed + " ns)");
            System.out.println("  " + book.getDisplayInfo());
        } else {
            System.out.println("  ✗ Book with title '" + title + "' not found.");
        }
    }

    private static void searchBooksByAuthor() {
        System.out.println("\n  ── SEARCH BY AUTHOR ──");
        System.out.print("  Enter author name (partial OK): ");
        String author = scanner.nextLine().trim();

        List<Book> results = catalog.findBooksByAuthor(author);
        if (results.isEmpty()) {
            System.out.println("  ✗ No books found by author: " + author);
        } else {
            System.out.println("  ✓ Found " + results.size() + " book(s):");
            for (Book b : results) {
                System.out.println("  " + b.getDisplayInfo());
            }
        }
    }

    private static void searchBooksByGenre() {
        System.out.println("\n  ── SEARCH BY GENRE ──");
        System.out.print("  Enter genre: ");
        String genre = scanner.nextLine().trim();

        List<Book> results = catalog.findBooksByGenre(genre);
        if (results.isEmpty()) {
            System.out.println("  ✗ No books found in genre: " + genre);
        } else {
            System.out.println("  ✓ Found " + results.size() + " book(s):");
            for (Book b : results) {
                System.out.println("  " + b.getDisplayInfo());
            }
        }
    }

    // ==================== SORTING ====================

    private static void displaySortedCatalog() {
        System.out.println("\n  ── SORT CATALOG ──");
        System.out.println("  Sort by: 1=Title, 2=Author, 3=ISBN, 4=Year, 5=Genre");
        System.out.print("  Choice: ");
        String sortChoice = scanner.nextLine().trim();

        System.out.println("  Algorithm: 1=Merge Sort, 2=Quick Sort");
        System.out.print("  Choice: ");
        String algoChoice = scanner.nextLine().trim();

        String criteria;
        switch (sortChoice) {
            case "1": criteria = "title"; break;
            case "2": criteria = "author"; break;
            case "3": criteria = "isbn"; break;
            case "4": criteria = "year"; break;
            case "5": criteria = "genre"; break;
            default: criteria = "title";
        }

        String algorithm = algoChoice.equals("2") ? "quick" : "merge";

        long start = System.nanoTime();
        List<Book> sorted = catalog.getBooksSortedBy(criteria, algorithm);
        long elapsed = System.nanoTime() - start;

        System.out.println("\n  Sorted by " + criteria.toUpperCase() +
                           " using " + algorithm.toUpperCase() + " SORT");
        System.out.println("  Time: " + elapsed + " ns\n");

        for (Book book : sorted) {
            System.out.println("  " + book.getDisplayInfo());
        }
    }

    // ==================== DEMONSTRATIONS ====================

    private static void demonstrateSorting() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║     SORTING ALGORITHM DEMONSTRATION          ║");
        System.out.println("║     Merge Sort O(n log n) vs Quick Sort      ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        List<Book> books = catalog.getBooks();
        SortService sortService = catalog.getSortService();

        // Merge Sort by Title
        long start = System.nanoTime();
        List<Book> mergeSortedTitle = sortService.mergeSort(
            new java.util.ArrayList<>(books), "title");
        long mergeTimeTitle = System.nanoTime() - start;

        System.out.println("\n  ── MERGE SORT BY TITLE (Stable, O(n log n)) ──");
        System.out.println("  Time: " + mergeTimeTitle + " ns");
        for (Book b : mergeSortedTitle) {
            System.out.println("    " + b.getIsbn() + " | " + b.getTitle());
        }

        // Quick Sort by Author
        start = System.nanoTime();
        List<Book> quickSortList = new java.util.ArrayList<>(books);
        sortService.quickSort(quickSortList, 0, quickSortList.size() - 1, "author");
        long quickTimeAuthor = System.nanoTime() - start;

        System.out.println("\n  ── QUICK SORT BY AUTHOR (In-place, O(n log n) avg) ──");
        System.out.println("  Time: " + quickTimeAuthor + " ns");
        for (Book b : quickSortList) {
            System.out.println("    " + b.getAuthor() + " | " + b.getTitle());
        }

        // Merge Sort by Year
        start = System.nanoTime();
        List<Book> mergeSortedYear = sortService.mergeSort(
            new java.util.ArrayList<>(books), "year");
        long mergeTimeYear = System.nanoTime() - start;

        System.out.println("\n  ── MERGE SORT BY YEAR ──");
        System.out.println("  Time: " + mergeTimeYear + " ns");
        for (Book b : mergeSortedYear) {
            System.out.println("    " + b.getPublicationYear() + " | " + b.getTitle());
        }

        // Quick Sort by Genre
        start = System.nanoTime();
        List<Book> quickSortGenre = new java.util.ArrayList<>(books);
        sortService.quickSort(quickSortGenre, 0, quickSortGenre.size() - 1, "genre");
        long quickTimeGenre = System.nanoTime() - start;

        System.out.println("\n  ── QUICK SORT BY GENRE ──");
        System.out.println("  Time: " + quickTimeGenre + " ns");
        for (Book b : quickSortGenre) {
            System.out.println("    " + b.getGenre() + " | " + b.getTitle());
        }

        System.out.println("\n  ── PERFORMANCE SUMMARY ──");
        System.out.println("  Merge Sort (Title): " + mergeTimeTitle + " ns");
        System.out.println("  Quick Sort (Author): " + quickTimeAuthor + " ns");
        System.out.println("  Merge Sort (Year):  " + mergeTimeYear + " ns");
        System.out.println("  Quick Sort (Genre): " + quickTimeGenre + " ns");
    }

    private static void demonstrateBinarySearch() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║    BINARY SEARCH DEMONSTRATION O(log n)      ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        SearchService searchService = catalog.getSearchService();
        SortService sortService = catalog.getSortService();
        List<Book> books = catalog.getBooks();

        // Binary search by ISBN
        String testISBN = "978-0132350884";
        System.out.println("\n  ── Binary Search by ISBN: " + testISBN + " ──");

        long start = System.nanoTime();
        Book found = searchService.binarySearchByISBN(books, testISBN);
        long elapsed = System.nanoTime() - start;

        if (found != null) {
            System.out.println("  ✓ FOUND in " + elapsed + " ns");
            System.out.println("  " + found.getDisplayInfo());
        }

        // Recursive binary search
        System.out.println("\n  ── Recursive Binary Search by ISBN ──");
        start = System.nanoTime();
        Book foundRecursive = searchService.recursiveBinarySearch(
            books, testISBN, 0, books.size() - 1);
        long elapsedRecursive = System.nanoTime() - start;

        if (foundRecursive != null) {
            System.out.println("  ✓ FOUND (Recursive) in " + elapsedRecursive + " ns");
            System.out.println("  " + foundRecursive.getDisplayInfo());
        }

        // Binary search by title
        List<Book> titleSorted = sortService.mergeSort(
            new java.util.ArrayList<>(books), "title");
        String testTitle = "Clean Code";
        System.out.println("\n  ── Binary Search by Title: '" + testTitle + "' ──");

        start = System.nanoTime();
        Book titleFound = searchService.binarySearchByTitle(titleSorted, testTitle);
        long titleElapsed = System.nanoTime() - start;

        if (titleFound != null) {
            System.out.println("  ✓ FOUND in " + titleElapsed + " ns");
            System.out.println("  " + titleFound.getDisplayInfo());
        }

        // Search for non-existent book
        String fakeISBN = "000-0000000000";
        System.out.println("\n  ── Binary Search for non-existent ISBN: " +
                           fakeISBN + " ──");
        start = System.nanoTime();
        Book notFound = searchService.binarySearchByISBN(books, fakeISBN);
        long notFoundElapsed = System.nanoTime() - start;
        System.out.println("  ✗ NOT FOUND (confirmed in " + notFoundElapsed + " ns)");

        // Comparison
        System.out.println("\n  ── COMPLEXITY ANALYSIS ──");
        System.out.println("  Catalog size: " + books.size() + " books");
        System.out.println("  Binary Search: O(log " + books.size() + ") = O(" +
                           (int)(Math.log(books.size()) / Math.log(2)) + ") comparisons max");
        System.out.println("  Linear Search: O(" + books.size() + ") comparisons max");
    }

    private static void demonstrateFineCalculation() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║   RECURSIVE FINE CALCULATION DEMONSTRATION   ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        FineService fineService = catalog.getTransactionService().getFineService();

        // Simple recursive fine
        System.out.println("\n  ── Simple Linear Fine (Recursion) ──");
        System.out.println("  Rate: $2.00/day");
        for (int days : new int[]{0, 1, 5, 10, 15}) {
            double fine = fineService.calculateFine(days, 2.0);
            System.out.printf("  %2d days overdue: $%.2f\n", days, fine);
        }

        // Compound recursive fine
        System.out.println("\n  ── Compound Fine (Recursion, 5%% daily increase) ──");
        System.out.println("  Base Rate: $2.00/day, Multiplier: 1.05x/day");
        for (int days : new int[]{0, 1, 5, 10, 15, 30}) {
            double fine = fineService.calculateCompoundFine(days, 2.0, 1.05);
            System.out.printf("  %2d days overdue: $%.2f\n", days, fine);
        }

        // Detailed breakdown
        System.out.println("\n  ── Fine Breakdown for 7 days overdue ──");
        System.out.println(fineService.getFineBreakdown(7));

        // Total fines for multiple books (recursive)
        System.out.println("  ── Total Fines for Multiple Overdue Books (Recursion) ──");
        int[] overdueDays = {3, 7, 0, 5, 10};
        System.out.print("  Overdue days per book: ");
        for (int d : overdueDays) System.out.print(d + " ");
        System.out.println();

        double totalFines = fineService.calculateTotalFines(overdueDays, 0);
        System.out.printf("  Total combined fine: $%.2f\n", totalFines);

        // Student vs Faculty grace period
        System.out.println("\n  ── Student vs Faculty Grace Period ──");
        System.out.println("  Student grace period: " +
                           fineService.getGracePeriodDays() + " days");
        System.out.println("  Faculty grace period: " +
                           fineService.getFacultyGraceDays() + " days");
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║      POLYMORPHISM DEMONSTRATION              ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        System.out.println("\n  ── Runtime Polymorphism (Method Overriding) ──");
        System.out.println("  Each entity type displays differently via getDisplayInfo():\n");

        // Books — different types display differently
        List<Book> books = catalog.getBooks();
        for (Book book : books) {
            // At runtime, JVM calls the correct overridden method
            System.out.println("  [" + book.getEntityType() + "] " + book.getDisplayInfo());
        }

        System.out.println("\n  ── Member Polymorphism ──\n");
        List<Member> members = catalog.getMembers();
        for (Member member : members) {
            System.out.println("  [" + member.getEntityType() + "] " + member.getDisplayInfo());
            System.out.println("    Max books allowed: " + member.getMaxBooksAllowed());

            // Demonstrate instanceof check (also polymorphism)
            if (member instanceof StudentMember) {
                StudentMember sm = (StudentMember) member;
                System.out.println("    Department: " + sm.getDepartment() +
                                   ", Grad Year: " + sm.getGraduationYear());
            } else if (member instanceof FacultyMember) {
                FacultyMember fm = (FacultyMember) member;
                System.out.println("    Department: " + fm.getDepartment() +
                                   ", Designation: " + fm.getDesignation());
            }
            System.out.println();
        }

        System.out.println("  ── Interface Polymorphism ──");
        System.out.println("  Searchable interface → SearchService implements binarySearchByISBN()");
        System.out.println("  Sortable interface   → SortService implements mergeSort(), quickSort()");
        System.out.println("  FineCalculable       → FineService implements calculateFine()");
        System.out.println("\n  Same interface, different implementations — that's polymorphism!");
    }
}