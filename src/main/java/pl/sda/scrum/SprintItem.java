package pl.sda.scrum;

import java.util.Optional;

public class SprintItem {
    private String title;
    private String description;

    public SprintItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Optional<String> assignedUser() {
        return Optional.empty();
    }
}
