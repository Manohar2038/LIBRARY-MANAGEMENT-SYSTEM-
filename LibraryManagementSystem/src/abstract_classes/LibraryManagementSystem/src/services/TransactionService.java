package services;

import abstract_classes.TransactionProcessor;
import exceptions.*;
import models.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles book issuance and return with robust error handling.
 * POLYMORPHISM: Different transaction processors for issue/return.
 * INHERITANCE: Issue and Return processors extend TransactionProcessor.
 */
public class TransactionService {

    private List<Transaction> transactionHistory;
    private int transactionCounter;
    private FineService fineService;

    public TransactionService() {
        this.transactionHistory = new ArrayList<>();
        this.transactionCounter = 0;
        this.fineService = new FineService();
    }

    /**
     * Issue a book to a member with full validation and error handling.
     */
    public Transaction issueBook(Book book, Member member)
            throws BookNotFoundException, BookAlreadyIssuedException,
                   MaxBooksExceededException {

        // Robust validation
        if (book == null) {
            throw new BookNotFoundException("NULL");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new BookAlreadyIssuedException(book.getIsbn());
        }

        if (!member.canIssueMore()) {
            throw new MaxBooksExceededException(member.getMemberId(),
                                                 member.getMaxBooksAllowed());
        }

        // Check if member already has this book
        if (member.getIssuedBookISBNs().contains(book.getIsbn())) {
            throw new BookAlreadyIssuedException(
                "Member already has a copy of ISBN: " + book.getIsbn());
        }

        // Process the issuance
        book.issueBook(member.getMemberId());
        member.addIssuedBook(book.getIsbn());

        // Create transaction record
        String txnId = generateTransactionId();
        Transaction transaction = new Transaction(
            txnId, Transaction.Type.ISSUE, book.getIsbn(), book.getTitle(),
            member.getMemberId(), member.getName(), 0.0,
            "Book issued successfully"
        );

        transactionHistory.add(transaction);
        return transaction;
    }

    /**
     * Return a book from a member with fine calculation and error handling.
     */
    public Transaction returnBook(Book book, Member member)
            throws BookNotFoundException, InvalidReturnException {

        if (book == null) {
            throw new BookNotFoundException("NULL");
        }

        // Check if this member actually has this book
        if (!member.getIssuedBookISBNs().contains(book.getIsbn())) {
            throw new InvalidReturnException(book.getIsbn(), member.getMemberId());
        }

        // Calculate fine using recursion-based fine service
        boolean isFaculty = member instanceof FacultyMember;
        double fine = fineService.calculateBorrowingFine(
            book.getIssueDateMillis(), isFaculty
        );

        // Process the return
        book.returnBook();
        member.removeIssuedBook(book.getIsbn());

        String remarks = "Book returned successfully";
        if (fine > 0) {
            member.addFine(fine);
            remarks += String.format(" | FINE: $%.2f applied", fine);
        }

        // Create transaction record
        String txnId = generateTransactionId();
        Transaction transaction = new Transaction(
            txnId, Transaction.Type.RETURN, book.getIsbn(), book.getTitle(),
            member.getMemberId(), member.getName(), fine, remarks
        );

        transactionHistory.add(transaction);
        return transaction;
    }

    private String generateTransactionId() {
        transactionCounter++;
        return String.format("TXN-%05d", transactionCounter);
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public List<Transaction> getMemberTransactions(String memberId) {
        List<Transaction> memberTxns = new ArrayList<>();
        for (Transaction txn : transactionHistory) {
            if (txn.getMemberId().equals(memberId)) {
                memberTxns.add(txn);
            }
        }
        return memberTxns;
    }

    public List<Transaction> getBookTransactions(String isbn) {
        List<Transaction> bookTxns = new ArrayList<>();
        for (Transaction txn : transactionHistory) {
            if (txn.getBookISBN().equals(isbn)) {
                bookTxns.add(txn);
            }
        }
        return bookTxns;
    }

    public FineService getFineService() {
        return fineService;
    }
}