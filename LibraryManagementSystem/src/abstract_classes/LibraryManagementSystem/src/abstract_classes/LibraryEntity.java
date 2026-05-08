package abstract_classes;

/**
 * ABSTRACTION: Abstract base class for all library entities.
 * Defines a contract that all entities must have an ID and display info.
 */
public abstract class LibraryEntity {
    private String id;
    private String name;

    public LibraryEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // ENCAPSULATION: Protected getters for subclass access
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Abstract method — forces every entity to define its own display
    public abstract String getDisplayInfo();

    // Abstract method — forces every entity to define its type
    public abstract String getEntityType();

    @Override
    public String toString() {
        return getEntityType() + " [ID=" + id + ", Name=" + name + "]";
    }
}