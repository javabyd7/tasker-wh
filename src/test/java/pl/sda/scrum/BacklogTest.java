package pl.sda.scrum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BacklogTest {

    @DisplayName("Can add new backlog item to backlog")
    @Test
    void test() {
        //given
        Backlog backlog = emptyBacklog();
        BacklogItem backlogItem = anyBacklogItem();
        //when
        backlog.add(backlogItem);
        //then
        assertThat(backlog.getItems()).containsExactly(backlogItem);

    }

    @DisplayName("Can commit backlogItem to sprint")
    @Test
    void commitToSprint() {
        //given
        BacklogItem item = anyBacklogItem();
        Backlog backlog = backlogWithItem(item);
        //when
        Sprint sprint = backlog.scheduleSprint();
        sprint.commitBacklogItem(item);
        //then
        assertThat(sprint.commitedItems()).hasSize(1);
    }

    @DisplayName("Given backlog with item " +
            "When commit ths item to sprint " +
            "Then Sprint has an Item with exactly same title, description but without assigned user")
    @Test
    void commitToSprint2() {
        //given
        BacklogItem item = backlogItemWithTitleAndDescription("feature#123","fix this bug");
        Backlog backlog =backlogWithItem(item);
        Sprint sprint = backlog.scheduleSprint();
        //when
        sprint.commitBacklogItem(item);
        //then
        assertThat(sprint.commitedItems()).hasSize(1);
        assertThat(sprint.commitedItems()).anyMatch(i -> "feature#123".equals(i.getTitle())
                && "fix this bug".equals(i.getDescription())
                && !i.assignedUser().isPresent());
    }

    private BacklogItem backlogItemWithTitleAndDescription(String title, String description) {
        return new BacklogItem(1L,title,description);
    }

    private Backlog backlogWithItem(BacklogItem item) {
        Backlog backlog = emptyBacklog();
        backlog.add(item);
        return backlog;
    }

    private BacklogItem anyBacklogItem() {
        return new BacklogItem(1L);
    }

    private Backlog emptyBacklog() {
        return new Backlog();
    }


}