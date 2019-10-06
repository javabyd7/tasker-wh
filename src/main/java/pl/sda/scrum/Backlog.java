package pl.sda.scrum;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Backlog {

    private  List<BacklogItem> backlogItems;

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
}
