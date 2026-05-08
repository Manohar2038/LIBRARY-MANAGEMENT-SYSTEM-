package services;

import interfaces.Sortable;
import models.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * DSA: Implements Merge Sort O(n log n) and Quick Sort O(n log n) average.
 * Supports sorting by: title, author, isbn, year, genre.
 */
public class SortService implements Sortable {

    // ==================== MERGE SORT — O(n log n) ====================

    /**
     * MERGE SORT — Stable sort, O(n log n) guaranteed.
     * Uses divide-and-conquer recursion.
     */
    @Override
    public List<Book> mergeSort(List<Book> books, String criteria) {
        if (books.size() <= 1) {
            return new ArrayList<>(books);  // Base case
        }

        int mid = books.size() / 2;

        // DIVIDE
        List<Book> leftHalf = mergeSort(new ArrayList<>(books.subList(0, mid)), criteria);
        List<Book> rightHalf = mergeSort(new ArrayList<>(books.subList(mid, books.size())), criteria);

        // CONQUER (Merge)
        return merge(leftHalf, rightHalf, criteria);
    }

    /**
     * Merge two sorted lists into one sorted list.
     */
    private List<Book> merge(List<Book> left, List<Book> right, String criteria) {
        List<Book> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (compare(left.get(i), right.get(j), criteria) <= 0) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

        // Add remaining elements
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }

    // ==================== QUICK SORT — O(n log n) average ====================

    /**
     * QUICK SORT — In-place sort, O(n log n) average.
     * Uses Lomuto partition scheme with recursion.
     */
    @Override
    public List<Book> quickSort(List<Book> books, int low, int high, String criteria) {
        if (low < high) {
            int pivotIndex = partition(books, low, high, criteria);
            quickSort(books, low, pivotIndex - 1, criteria);   // Sort left partition
            quickSort(books, pivotIndex + 1, high, criteria);  // Sort right partition
        }
        return books;
    }

    /**
     * Lomuto Partition — selects last element as pivot.
     */
    private int partition(List<Book> books, int low, int high, String criteria) {
        Book pivot = books.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(books.get(j), pivot, criteria) <= 0) {
                i++;
                swap(books, i, j);
            }
        }
        swap(books, i + 1, high);
        return i + 1;
    }

    /**
     * Swap two elements in the list.
     */
    private void swap(List<Book> books, int i, int j) {
        Book temp = books.get(i);
        books.set(i, books.get(j));
        books.set(j, temp);
    }

    /**
     * POLYMORPHIC comparison — compares books based on different criteria.
     */
    private int compare(Book a, Book b, String criteria) {
        switch (criteria.toLowerCase()) {
            case "title":
                return a.getTitle().compareToIgnoreCase(b.getTitle());
            case "author":
                return a.getAuthor().compareToIgnoreCase(b.getAuthor());
            case "isbn":
                return a.getIsbn().compareToIgnoreCase(b.getIsbn());
            case "year":
                return Integer.compare(a.getPublicationYear(), b.getPublicationYear());
            case "genre":
                return a.getGenre().compareToIgnoreCase(b.getGenre());
            default:
                return a.getTitle().compareToIgnoreCase(b.getTitle());
        }
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Print sorting comparison for demonstration.
     */
    public void demonstrateSorting(List<Book> books) {
        System.out.println("\n===== SORTING DEMONSTRATION =====\n");

        // Merge Sort by Title
        long start = System.nanoTime();
        List<Book> mergeSorted = mergeSort(new ArrayList<>(books), "title");
        long mergeTime = System.nanoTime() - start;

        System.out.println("MERGE SORT (by Title) — Time: " + mergeTime + " ns");
        for (Book b : mergeSorted) {
            System.out.println("  " + b.getIsbn() + " | " + b.getTitle());
        }

        // Quick Sort by Author
        start = System.nanoTime();
        List<Book> quickSortList = new ArrayList<>(books);
        quickSort(quickSortList, 0, quickSortList.size() - 1, "author");
        long quickTime = System.nanoTime() - start;

        System.out.println("\nQUICK SORT (by Author) — Time: " + quickTime + " ns");
        for (Book b : quickSortList) {
            System.out.println("  " + b.getIsbn() + " | " + b.getAuthor() + " | " + b.getTitle());
        }
    }
}