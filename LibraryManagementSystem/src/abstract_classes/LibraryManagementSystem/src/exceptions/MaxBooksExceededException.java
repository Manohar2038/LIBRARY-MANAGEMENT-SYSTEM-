package exceptions;

public class MaxBooksExceededException extends Exception {
    private String memberId;
    private int maxAllowed;

    public MaxBooksExceededException(String memberId, int maxAllowed) {
        super("Member '" + memberId + "' has reached the maximum limit of " +
              maxAllowed + " books.");
        this.memberId = memberId;
        this.maxAllowed = maxAllowed;
    }

    public String getMemberId() { return memberId; }
    public int getMaxAllowed() { return maxAllowed; }
}