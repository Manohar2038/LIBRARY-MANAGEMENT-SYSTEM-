package models;

/**
 * INHERITANCE: Extends Member with student-specific attributes.
 * POLYMORPHISM: Overrides getDisplayInfo() and getEntityType().
 */
public class StudentMember extends Member {
    private String studentId;
    private String department;
    private int graduationYear;

    // Students can borrow max 3 books
    private static final int STUDENT_MAX_BOOKS = 3;

    public StudentMember(String memberId, String name, String email, String phone,
                         String studentId, String department, int graduationYear) {
        super(memberId, name, email, phone, STUDENT_MAX_BOOKS);
        this.studentId = studentId;
        this.department = department;
        this.graduationYear = graduationYear;
    }

    public String getStudentId() { return studentId; }
    public String getDepartment() { return department; }
    public int getGraduationYear() { return graduationYear; }

    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + " [Student: " + department +
               ", Grad:" + graduationYear + "]";
    }

    @Override
    public String getEntityType() {
        return "STUDENT-MEMBER";
    }
}