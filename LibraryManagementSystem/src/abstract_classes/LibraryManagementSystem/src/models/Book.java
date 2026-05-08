package models;

import abstract_classes.LibraryEntity;

/**
 * ENCAPSULATION: All fields are private with controlled access via getters/setters.
 * INHERITANCE: Extends LibraryEntity.
 */
public class Book extends LibraryEntity implements Comparable<Book> {
    private String isbn;
    private String author;
    private String genre;
    private int publicationYear;
    private boolean isIssued;
    private String issuedToMemberId;
    private long issueDateMillis;
    private int totalCopies;
    private int availableCopies;

    public Book(String isbn, String title, String author, String genre,
                int publicationYear, int totalCopies) {
        super(isbn, title);
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isIssued = false;
        this.issuedToMemberId = null;
        this.issueDateMillis = 0;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // --- ENCAPSULATION: Getters and Setters ---
    public String getIsbn() { return isbn; }
    public String getTitle() { return getName(); }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public int getPublicationYear() { return publicationYear; }
    public boolean isIssued() { return availableCopies == 0; }
    public String getIssuedToMemberId() { return issuedToMemberId; }
    public long getIssueDateMillis() { return issueDateMillis; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setPublicationYear(int year) { this.publicationYear = year; }

    public void issueBook(String memberId) {
        if (availableCopies > 0) {
            this.availableCopies--;
            this.issuedToMemberId = memberId;
            this.issueDateMillis = System.currentTimeMillis();
            this.isIssued = (availableCopies == 0);
        }
    }

    public void returnBook() {
        if (availableCopies < totalCopies) {
            this.availableCopies++;
            if (availableCopies > 0) {
                this.isIssued = false;
            }
        }
    }

    // POLYMORPHISM: Implementing abstract methods from LibraryEntity
    @Override
    public String getDisplayInfo() {
        return String.format(
            "| %-13s | %-30s | %-20s | %-12s | %-4d | %d/%d available |",
            isbn, getTitle(), author, genre, publicationYear, availableCopies, totalCopies
        );
    }

    @Override
    public String getEntityType() {
        return "BOOK";
    }

    // Comparable implementation for sorting
    @Override
    public int compareTo(Book other) {
        return this.getTitle().compareToIgnoreCase(other.getTitle());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}