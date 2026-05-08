package models;

/**
 * INHERITANCE: Extends Book with printed book specific fields.
 * POLYMORPHISM: Overrides getDisplayInfo() and getEntityType().
 */
public class PrintedBook extends Book {
    private int numberOfPages;
    private String shelfLocation;
    private String condition; // NEW, GOOD, FAIR, POOR

    public PrintedBook(String isbn, String title, String author, String genre,
                       int publicationYear, int totalCopies,
                       int numberOfPages, String shelfLocation, String condition) {
        super(isbn, title, author, genre, publicationYear, totalCopies);
        this.numberOfPages = numberOfPages;
        this.shelfLocation = shelfLocation;
        this.condition = condition;
    }

    public int getNumberOfPages() { return numberOfPages; }
    public String getShelfLocation() { return shelfLocation; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    // POLYMORPHISM: Override display for printed book specific info
    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + " [Printed: " + numberOfPages +
               "pp, Shelf:" + shelfLocation + ", " + condition + "]";
    }

    @Override
    public String getEntityType() {
        return "PRINTED-BOOK";
    }
}