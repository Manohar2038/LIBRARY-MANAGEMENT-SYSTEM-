package models;

import abstract_classes.LibraryEntity;

public abstract class Member extends LibraryEntity {
    protected String name;
    protected int maxBooks;

    public Member(String id, String name, int maxBooks) {
        super(id);
        this.name = name;
        this.maxBooks = maxBooks;
    }

    public String getName() {
        return name;
    }

    public int getMaxBooks() {
        return maxBooks;
    }
}

