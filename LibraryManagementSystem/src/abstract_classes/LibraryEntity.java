package abstract_classes;

public abstract class LibraryEntity {
    protected String id;

    public LibraryEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

