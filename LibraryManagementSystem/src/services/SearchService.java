package services;

import java.util.ArrayList;
import java.util.List;

import interfaces.Searchable;
import models.Book;

public class SearchService implements Searchable<Book> {
    private final LibraryCatalog catalog;

    public SearchService(LibraryCatalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public Book searchById(String id) {
        return catalog.getBookById(id);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        // Minimal implementation (catalog currently exposes only getBookById)
        // Kept simple for this scaffold.
        return new ArrayList<>();
    }
}

