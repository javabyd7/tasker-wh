package pl.sda.scrum.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class BacklogItem {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;

    public BacklogItem(Long id) {
        this.id = id;
    }

    public BacklogItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public BacklogItem(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
