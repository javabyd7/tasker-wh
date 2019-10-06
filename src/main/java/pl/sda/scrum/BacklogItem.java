package pl.sda.scrum;

public class BacklogItem {
    private final Long itemId;
    private String title;
    private String description;



    public BacklogItem(Long itemId) {
        this.itemId = itemId;
    }

    public BacklogItem(Long itemId, String title, String description) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return itemId;
    }
}
