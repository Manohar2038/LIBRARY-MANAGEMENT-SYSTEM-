package services;

import interfaces.FineCalculable;

/**
 * DSA: Uses RECURSION for fine calculations.
 */
public class FineService implements FineCalculable {

    private static final double DAILY_FINE_RATE = 2.0;         // $2 per day
    private static final double COMPOUND_MULTIPLIER = 1.05;    // 5% daily increase
    private static final int GRACE_PERIOD_DAYS = 14;           // 2 weeks borrowing period
    private static final int FACULTY_GRACE_DAYS = 30;          // Faculty gets 30 days

    /**
     * RECURSION: Calculate simple linear fine.
     * Base case: daysOverdue <= 0 → no fine.
     * Recursive case: dailyRate + calculateFine(daysOverdue - 1, dailyRate)
     */
    @Override
    public double calculateFine(int daysOverdue, double dailyRate) {
        // Base case
        if (daysOverdue <= 0) {
            return 0.0;
        }
        // Recursive case: add one day's fine + rest
        return dailyRate + calculateFine(daysOverdue - 1, dailyRate);
    }

    /**
     * RECURSION: Calculate compound fine (increases daily).
     * Each subsequent day's fine is multiplied by the multiplier.
     * Base case: daysOverdue <= 0 → no fine.
     * Recursive case: currentDayFine + compound(remaining days with increased rate)
     */
    @Override
    public double calculateCompoundFine(int daysOverdue, double baseRate, double multiplier) {
        // Base case
        if (daysOverdue <= 0) {
            return 0.0;
        }
        // Current day's fine + recursive call with increased rate
        return baseRate + calculateCompoundFine(daysOverdue - 1,
                                                 baseRate * multiplier, multiplier);
    }

    /**
     * Calculate fine for a borrowing period.
     * Uses the grace period to determine overdue days.
     */
    public double calculateBorrowingFine(long issueDateMillis, boolean isFaculty) {
        long currentTime = System.currentTimeMillis();
        long elapsedMillis = currentTime - issueDateMillis;
        int elapsedDays = (int) (elapsedMillis / (1000 * 60 * 60 * 24));

        int gracePeriod = isFaculty ? FACULTY_GRACE_DAYS : GRACE_PERIOD_DAYS;
        int overdueDays = elapsedDays - gracePeriod;

        if (overdueDays <= 0) {
            return 0.0;
        }

        // Use compound fine for overdue books
        return calculateCompoundFine(overdueDays, DAILY_FINE_RATE, COMPOUND_MULTIPLIER);
    }

    /**
     * RECURSION: Calculate total fine for multiple overdue books.
     */
    public double calculateTotalFines(int[] overdueDaysArray, int index) {
        // Base case
        if (index >= overdueDaysArray.length) {
            return 0.0;
        }
        // Recursive case
        double currentFine = calculateFine(overdueDaysArray[index], DAILY_FINE_RATE);
        return currentFine + calculateTotalFines(overdueDaysArray, index + 1);
    }

    /**
     * Get fine breakdown as a formatted string.
     */
    public String getFineBreakdown(int daysOverdue) {
        StringBuilder sb = new StringBuilder();
        sb.append("  Fine Breakdown:\n");
        sb.append("  ─────────────────────────────────\n");

        double runningTotal = 0;
        double rate = DAILY_FINE_RATE;

        for (int day = 1; day <= daysOverdue; day++) {
            runningTotal += rate;
            sb.append(String.format("  Day %2d: $%.2f (Running: $%.2f)\n",
                      day, rate, runningTotal));
            rate *= COMPOUND_MULTIPLIER;
        }

        sb.append("  ─────────────────────────────────\n");
        sb.append(String.format("  TOTAL FINE: $%.2f\n", runningTotal));
        return sb.toString();
    }

    // Getters for constants
    public double getDailyFineRate() { return DAILY_FINE_RATE; }
    public int getGracePeriodDays() { return GRACE_PERIOD_DAYS; }
    public int getFacultyGraceDays() { return FACULTY_GRACE_DAYS; }
}