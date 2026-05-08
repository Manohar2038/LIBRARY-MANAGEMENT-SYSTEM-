package models;

import abstract_classes.LibraryEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * ENCAPSULATION: All member data is private with controlled access.
 * INHERITANCE: Extends LibraryEntity.
 */
public class Member extends LibraryEntity {
    private String email;
    private String phone;
    private List<String> issuedBookISBNs;
    private int maxBooksAllowed;
    private double totalFinesPaid;
    private double outstandingFines;

    public Member(String memberId, String name, String email, String phone,
                  int maxBooksAllowed) {
        super(memberId, name);
        this.email = email;
        this.phone = phone;
        this.issuedBookISBNs = new ArrayList<>();
        this.maxBooksAllowed = maxBooksAllowed;
        this.totalFinesPaid = 0.0;
        this.outstandingFines = 0.0;
    }

    // ENCAPSULATION: Controlled access
    public String getMemberId() { return getId(); }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<String> getIssuedBookISBNs() { return new ArrayList<>(issuedBookISBNs); }
    public int getMaxBooksAllowed() { return maxBooksAllowed; }
    public double getTotalFinesPaid() { return totalFinesPaid; }
    public double getOutstandingFines() { return outstandingFines; }
    public int getCurrentIssuedCount() { return issuedBookISBNs.size(); }

    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean canIssueMore() {
        return issuedBookISBNs.size() < maxBooksAllowed;
    }

    public void addIssuedBook(String isbn) {
        issuedBookISBNs.add(isbn);
    }

    public boolean removeIssuedBook(String isbn) {
        return issuedBookISBNs.remove(isbn);
    }

    public void addFine(double amount) {
        this.outstandingFines += amount;
    }

    public void payFine(double amount) {
        double payment = Math.min(amount, outstandingFines);
        this.outstandingFines -= payment;
        this.totalFinesPaid += payment;
    }

    @Override
    public String getDisplayInfo() {
        return String.format(
            "| %-8s | %-20s | %-25s | %-12s | Books: %d/%d | Fines: $%.2f |",
            getId(), getName(), email, phone,
            issuedBookISBNs.size(), maxBooksAllowed, outstandingFines
        );
    }

    @Override
    public String getEntityType() {
        return "MEMBER";
    }
}