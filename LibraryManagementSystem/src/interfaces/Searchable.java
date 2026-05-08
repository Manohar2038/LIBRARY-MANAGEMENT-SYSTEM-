package interfaces;

import java.util.List;

public interface Searchable<T> {
    T searchById(String id);
    List<T> searchByTitle(String title);
}

