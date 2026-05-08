package services;

import java.util.Comparator;
import java.util.List;

import interfaces.Sortable;
import models.Book;

public class SortService implements Sortable<Book> {
    @Override
    public List<Book> sortByTitle(List<Book> items) {
        items.sort(Comparator.comparing(Book::getTitle));
        return items;
    }
}

