package services;

import interfaces.Searchable;
import models.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * DSA: Implements Binary Search for O(log n) lookups.
 * Books must be sorted by the search key before calling binary search.
 */
public class SearchService implements Searchable {

    /**
     * BINARY SEARCH by ISBN — O(log n)
     * Prerequisite: List must be sorted by ISBN.
     */
    @Override
    public Book binarySearchByISBN(List<Book> sortedBooks, String isbn) {
        int low = 0;
        int high = sortedBooks.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midISBN = sortedBooks.get(mid).getIsbn();
            int comparison = midISBN.compareToIgnoreCase(isbn);

            if (comparison == 0) {
                return sortedBooks.get(mid);  // Found
            } else if (comparison < 0) {
                low = mid + 1;  // Search right half
            } else {
                high = mid - 1; // Search left half
            }
        }
        return null; // Not found
    }

    /**
     * BINARY SEARCH by Title — O(log n)
     * Prerequisite: List must be sorted by Title.
     */
    @Override
    public Book binarySearchByTitle(List<Book> sortedBooks, String title) {
        int low = 0;
        int high = sortedBooks.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            String midTitle = sortedBooks.get(mid).getTitle();
            int comparison = midTitle.compareToIgnoreCase(title);

            if (comparison == 0) {
                return sortedBooks.get(mid);
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    /**
     * RECURSIVE Binary Search variant — O(log n)
     */
    public Book recursiveBinarySearch(List<Book> sortedBooks, String isbn,
                                       int low, int high) {
        // Base case
        if (low > high) {
            return null;
        }

        int mid = low + (high - low) / 2;
        int comparison = sortedBooks.get(mid).getIsbn().compareToIgnoreCase(isbn);

        if (comparison == 0) {
            return sortedBooks.get(mid);
        } else if (comparison < 0) {
            return recursiveBinarySearch(sortedBooks, isbn, mid + 1, high); // Recurse right
        } else {
            return recursiveBinarySearch(sortedBooks, isbn, low, mid - 1);  // Recurse left
        }
    }

    /**
     * Linear search by Author — O(n) (multiple results possible)
     */
    @Override
    public List<Book> searchByAuthor(List<Book> books, String author) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Linear search by Genre — O(n) (multiple results possible)
     */
    @Override
    public List<Book> searchByGenre(List<Book> books, String genre) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                results.add(book);
            }
        }
        return results;
    }
}