package exceptions;

public class InvalidReturnException extends Exception {
    private String isbn;
    private String memberId;

    public InvalidReturnException(String isbn, String memberId) {
        super("Book '" + isbn + "' was not issued to member '" + memberId + "'.");
        this.isbn = isbn;
        this.memberId = memberId;
    }

    public String getIsbn() { return isbn; }
    public String getMemberId() { return memberId; }
}