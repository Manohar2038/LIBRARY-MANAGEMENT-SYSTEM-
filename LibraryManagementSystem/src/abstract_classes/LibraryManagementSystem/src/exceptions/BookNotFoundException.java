package exceptions;

public class BookNotFoundException extends Exception {
    private String isbn;

    public BookNotFoundException(String isbn) {
        super("Book with ISBN '" + isbn + "' was not found in the catalog.");
        this.isbn = isbn;
    }

    public String getIsbn() { return isbn; }
}