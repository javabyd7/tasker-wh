package pl.sda.scrum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sda.task.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class CommitSprintTest {

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
    @DisplayName("Given Sprint with 1 comitedItem without assigned user " +
            "When confirm sprint " +
            "Then confirmation fails")
    @Test
    void confirmFails() {
        //given
        Sprint sprint = sprintWithSingleUnasigneItem();
        //when
        CannotConfirmSprintException exception = catchThrowableOfType(() -> sprint.confirm(), CannotConfirmSprintException.class);
        //then
        assertThat(exception).isNotNull();

    }
    @DisplayName("Given Sprint with commitedItem with Assigned user " +
            "When confirm sprint " +
            "Then confirmation succes")
    @Test
    void confirmSucces() {
        //given
        BacklogItem item = new BacklogItem(1L,"feature#123","fix");
        Sprint sprint = sprintWithSingleUnasigneItem(item);
        User user = new User(2L,"Janek",false);
        sprint.assignItemToUser(item.getId(),user);
        //when
        sprint.confirm();
        //then
        assertThat(sprint.isConfirmed()).isTrue();

    }
    @DisplayName("Given sprint with one unfinished item " +
            "When finish this item by owner " +
            "Then sprint is finished/closed/done/whatever")
    @Test
    void markItemAsDone() {
        //given
        BacklogItem item = anyBacklogItem();
        User owner = ownerWithNameAndId("Andrew", 1L);
        Sprint sprint = sprintWithSingleAssignedItem(item, owner);
        //when
        sprint.markItemAsFinished(item.getId(),owner);
        //then
        assertThat(sprint.isDone()).isTrue();
        assertThat(sprint.finishedItems()).hasSize(1);
        assertThat(sprint.finishedItems().get(0).getId()).isEqualTo(item.getId());
    }
    @DisplayName("Given sprint with 2 unfinished items " +
            "When finish just one of them " +
            "Then sprint is not finished yet")
    @Test
    void markItemAsDone2() {
        //given
        BacklogItem firstItem = backlogItemWithId(1L);
        BacklogItem secondItem = backlogItemWithId(2L);
        User owner = ownerWithNameAndId("Andrew", 3L);
        Sprint sprint = sprintWithUnassignedItems(firstItem, secondItem);
        sprint.assignItemToUser(firstItem.getId(),owner);

        //when
        sprint.markItemAsFinished(firstItem.getId(),owner);
        //then
        assertThat(sprint.isDone()).isFalse();
        assertThat(sprint.finishedItems()).hasSize(1);
        assertThat(sprint.finishedItems().get(0).getId()).isEqualTo(firstItem.getId());
    }

    private Sprint sprintWithUnassignedItems(BacklogItem... items) {
        Backlog backlog = emptyBacklog();
        Sprint sprint = backlog.scheduleSprint();
        for (BacklogItem item: items
             ) {
            sprint.commitBacklogItem(item);
        }
        return sprint;
    }

    private BacklogItem backlogItemWithId(Long id) {
        return new BacklogItem(id);
    }

    private Sprint sprintWithSingleAssignedItem(BacklogItem item, User owner) {
        Sprint sprint = sprintWithSingleUnasigneItem(item);
        sprint.assignItemToUser(item.getId(),owner);
        return sprint;
    }

    private User ownerWithNameAndId(String name, Long id) {
        return new User(id,name,false);
    }

    private Sprint sprintWithSingleUnasigneItem(BacklogItem item) {
        Backlog backlog = backlogWithItem(item);
        Sprint sprint = backlog.scheduleSprint();
        sprint.commitBacklogItem(item);
        return sprint;
    }

    private Sprint sprintWithSingleUnasigneItem() {
        BacklogItem item = backlogItemWithTitleAndDescription("feature#123","fix this bug");
        Backlog backlog =backlogWithItem(item);
        Sprint sprint = backlog.scheduleSprint();
        sprint.commitBacklogItem(anyUnasignedBacklogItem());
        return sprint;
    }

    private BacklogItem anyUnasignedBacklogItem() {
        return anyBacklogItem();
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