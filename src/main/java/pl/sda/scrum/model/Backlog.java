package pl.sda.scrum.model;

import com.google.common.collect.ImmutableList;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Backlog {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BacklogItem> backlogItems;

    public Backlog() {
        backlogItems = new ArrayList<>();
    }

    public void add(BacklogItem backlogItem) {
        backlogItems.add(backlogItem);
    }

    public List<BacklogItem> getItems() {
        return ImmutableList.copyOf(backlogItems);
    }

    public Sprint scheduleSprint() {

        return new Sprint();
    }

    public Optional<BacklogItem> findItemById(Long itemId) {
        return backlogItems.stream().filter(backlogItem -> backlogItem.getId().equals(itemId)).findAny();
    }
}
