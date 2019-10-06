package pl.sda.scrum;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Sprint {
    private List<SprintItem> sprintItems;

    public Sprint() {
        sprintItems = new ArrayList<>();
    }

    public void commitBacklogItem(BacklogItem item) {
        SprintItem sprintItem = new SprintItem(item.getTitle(), item.getDescription());
        sprintItems.add(sprintItem);
    }

    public List<SprintItem> commitedItems() {
        return ImmutableList.copyOf(sprintItems);
    }
}
