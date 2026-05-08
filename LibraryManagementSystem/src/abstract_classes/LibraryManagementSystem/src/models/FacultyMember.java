package models;

/**
 * INHERITANCE: Extends Member with faculty-specific attributes.
 * POLYMORPHISM: Overrides getDisplayInfo() and getEntityType().
 */
public class FacultyMember extends Member {
    private String employeeId;
    private String department;
    private String designation;

    // Faculty can borrow max 10 books
    private static final int FACULTY_MAX_BOOKS = 10;

    public FacultyMember(String memberId, String name, String email, String phone,
                         String employeeId, String department, String designation) {
        super(memberId, name, email, phone, FACULTY_MAX_BOOKS);
        this.employeeId = employeeId;
        this.department = department;
        this.designation = designation;
    }

    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }
    public String getDesignation() { return designation; }

    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + " [Faculty: " + department +
               ", " + designation + "]";
    }

    @Override
    public String getEntityType() {
        return "FACULTY-MEMBER";
    }
}