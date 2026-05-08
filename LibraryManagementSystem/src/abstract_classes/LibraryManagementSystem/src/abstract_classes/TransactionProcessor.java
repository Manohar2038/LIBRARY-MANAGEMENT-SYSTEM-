package abstract_classes;

import models.Book;
import models.Member;

/**
 * ABSTRACTION: Abstract class defining template for transaction processing.
 * Uses Template Method Pattern.
 */
public abstract class TransactionProcessor {

    // Template method — defines the skeleton of the algorithm
    public final String processTransaction(Book book, Member member) {
        String validation = validate(book, member);
        if (validation != null) {
            return "FAILED: " + validation;
        }
        execute(book, member);
        String receipt = generateReceipt(book, member);
        log(book, member);
        return receipt;
    }

    protected abstract String validate(Book book, Member member);

    protected abstract void execute(Book book, Member member);

    protected abstract String generateReceipt(Book book, Member member);

    protected abstract void log(Book book, Member member);
}