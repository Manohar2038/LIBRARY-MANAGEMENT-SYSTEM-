package interfaces;

/**
 * ABSTRACTION via Interface: Contract for fine calculations using recursion.
 */
public interface FineCalculable {
    double calculateFine(int daysOverdue, double dailyRate);
    double calculateCompoundFine(int daysOverdue, double baseRate, double multiplier);
}