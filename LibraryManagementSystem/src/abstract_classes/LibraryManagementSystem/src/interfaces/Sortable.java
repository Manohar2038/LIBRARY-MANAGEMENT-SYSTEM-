package interfaces;

import models.Book;
import java.util.List;

/**
 * ABSTRACTION via Interface: Contract for sorting operations.
 */
public interface Sortable {
    List<Book> mergeSort(List<Book> books, String criteria);
    List<Book> quickSort(List<Book> books, int low, int high, String criteria);
}