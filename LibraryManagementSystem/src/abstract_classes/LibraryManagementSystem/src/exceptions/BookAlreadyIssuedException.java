package exceptions;

public class BookAlreadyIssuedException extends Exception {
    private String isbn;

    public BookAlreadyIssuedException(String isbn) {
        super("Book with ISBN '" + isbn + "' has no available copies.");
        this.isbn = isbn;
    }

    public String getIsbn() { return isbn; }
}