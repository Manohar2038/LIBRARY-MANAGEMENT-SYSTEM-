package models;

public class PrintedBook extends Book {
    private int totalPages;

    public PrintedBook(String id, String title, int totalPages) {
        super(id, title);
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }
}

