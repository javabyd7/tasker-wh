package pl.sda.task.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String description;

    public sta void setUser(User user) {
    }
}
