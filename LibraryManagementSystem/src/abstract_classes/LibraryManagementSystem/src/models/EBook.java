package models;

/**
 * INHERITANCE: Extends Book with additional e-book specific fields.
 * POLYMORPHISM: Overrides getDisplayInfo() and getEntityType().
 */
public class EBook extends Book {
    private String fileFormat; // PDF, EPUB, MOBI
    private double fileSizeMB;
    private String downloadLink;

    public EBook(String isbn, String title, String author, String genre,
                 int publicationYear, int totalCopies,
                 String fileFormat, double fileSizeMB, String downloadLink) {
        super(isbn, title, author, genre, publicationYear, totalCopies);
        this.fileFormat = fileFormat;
        this.fileSizeMB = fileSizeMB;
        this.downloadLink = downloadLink;
    }

    public String getFileFormat() { return fileFormat; }
    public double getFileSizeMB() { return fileSizeMB; }
    public String getDownloadLink() { return downloadLink; }

    // POLYMORPHISM: Override display for e-book specific info
    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + " [E-Book: " + fileFormat +
               ", " + fileSizeMB + "MB]";
    }

    @Override
    public String getEntityType() {
        return "E-BOOK";
    }
}