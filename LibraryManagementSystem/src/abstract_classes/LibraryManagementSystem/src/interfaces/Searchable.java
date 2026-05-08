package interfaces;

import models.Book;
import java.util.List;

/**
 * ABSTRACTION via Interface: Contract for search operations.
 */
public interface Searchable {
    Book binarySearchByISBN(List<Book> books, String isbn);
    Book binarySearchByTitle(List<Book> sortedBooks, String title);
    List<Book> searchByAuthor(List<Book> books, String author);
    List<Book> searchByGenre(List<Book> books, String genre);
}