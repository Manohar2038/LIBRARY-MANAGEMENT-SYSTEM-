package interfaces;

import java.util.List;

public interface Sortable<T> {
    List<T> sortByTitle(List<T> items);
}

