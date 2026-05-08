package models;

public class EBook extends Book {
    private String fileFormat;

    public EBook(String id, String title, String fileFormat) {
        super(id, title);
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }
}

