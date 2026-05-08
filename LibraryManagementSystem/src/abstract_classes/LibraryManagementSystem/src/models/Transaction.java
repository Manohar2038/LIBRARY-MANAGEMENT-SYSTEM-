package models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ENCAPSULATION: Immutable transaction record.
 */
public class Transaction {
    public enum Type { ISSUE, RETURN }

    private final String transactionId;
    private final Type type;
    private final String bookISBN;
    private final String bookTitle;
    private final String memberId;
    private final String memberName;
    private final long timestamp;
    private final double fineAmount;
    private final String remarks;

    public Transaction(String transactionId, Type type, String bookISBN,
                       String bookTitle, String memberId, String memberName,
                       double fineAmount, String remarks) {
        this.transactionId = transactionId;
        this.type = type;
        this.bookISBN = bookISBN;
        this.bookTitle = bookTitle;
        this.memberId = memberId;
        this.memberName = memberName;
        this.timestamp = System.currentTimeMillis();
        this.fineAmount = fineAmount;
        this.remarks = remarks;
    }

    // All getters, no setters — immutable
    public String getTransactionId() { return transactionId; }
    public Type getType() { return type; }
    public String getBookISBN() { return bookISBN; }
    public String getBookTitle() { return bookTitle; }
    public String getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public long getTimestamp() { return timestamp; }
    public double getFineAmount() { return fineAmount; }
    public String getRemarks() { return remarks; }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }

    @Override
    public String toString() {
        return String.format(
            "| %-10s | %-6s | %-13s | %-25s | %-8s | %-15s | $%-7.2f | %s |",
            transactionId, type, bookISBN, bookTitle, memberId, memberName,
            fineAmount, getFormattedDate()
        );
    }
}