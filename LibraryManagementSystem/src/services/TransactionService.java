package services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import abstract_classes.TransactionProcessor;
import exceptions.BookAlreadyIssuedException;
import exceptions.BookNotFoundException;
import exceptions.MemberNotFoundException;
import models.Book;
import models.Member;
import models.Transaction;

public class TransactionService extends TransactionProcessor {
    private final LibraryCatalog catalog;
    private final Map<String, Member> members = new HashMap<>();
    private final Map<String, Transaction> activeTransactions = new HashMap<>();

    public TransactionService(LibraryCatalog catalog) {
        this.catalog = catalog;
    }

    public void registerMember(Member member) {
        members.put(member.getId(), member);
    }

    public void issueBook(String bookId, String memberId, LocalDate issueDate)
            throws BookNotFoundException, MemberNotFoundException, BookAlreadyIssuedException {
        Book book = catalog.getBookById(bookId);
        if (book == null) throw new BookNotFoundException("Book not found: " + bookId);

        Member member = members.get(memberId);
        if (member == null) throw new MemberNotFoundException("Member not found: " + memberId);

        if (book.isIssued()) throw new BookAlreadyIssuedException("Book already issued: " + bookId);

        Transaction tx = new Transaction(book, member, issueDate);
        book.setIssued(true);
        activeTransactions.put(bookId, tx);
    }

    public void returnBook(String bookId, LocalDate returnDate)
            throws BookNotFoundException, IllegalArgumentException {
        Transaction tx = activeTransactions.get(bookId);
        if (tx == null) throw new IllegalArgumentException("No active transaction for book: " + bookId);

        tx.markReturned(returnDate);
        tx.getBook().setIssued(false);
        activeTransactions.remove(bookId);
    }

    @Override
    public void processTransaction(Transaction transaction) {
        // Not used in scaffold.
        // You can route to issue/return based on transaction state if needed.
    }
}

