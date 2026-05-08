package models;

import abstract_classes.LibraryEntity;

public abstract class Book extends LibraryEntity {
    protected String title;
    protected boolean issued;

    public Book(String id, String title) {
        super(id);
        this.title = title;
        this.issued = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }
}

