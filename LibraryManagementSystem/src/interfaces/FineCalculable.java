package interfaces;

import java.time.LocalDate;

public interface FineCalculable {
    double calculateFine(LocalDate issueDate, LocalDate returnDate);
}

