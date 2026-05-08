package services;

import java.util.HashMap;
import java.util.Map;

import models.Book;

public class LibraryCatalog {
    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public Book getBookById(String id) {
        return books.get(id);
    }
}

