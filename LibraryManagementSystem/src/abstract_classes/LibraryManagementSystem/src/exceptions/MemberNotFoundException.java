package exceptions;

public class MemberNotFoundException extends Exception {
    private String memberId;

    public MemberNotFoundException(String memberId) {
        super("Member with ID '" + memberId + "' was not found.");
        this.memberId = memberId;
    }

    public String getMemberId() { return memberId; }
}