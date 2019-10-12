package pl.sda.scrum.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.sda.common.user.User;
import pl.sda.common.user.UserRepository;
import pl.sda.scrum.model.Backlog;
import pl.sda.scrum.model.BacklogItem;
import pl.sda.scrum.model.Sprint;
import pl.sda.scrum.model.SprintItem;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SprintServiceIntegrationTest {

    @Autowired
    SprintService sprintService;
    @Autowired
    BacklogService backlogService;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("schedule new sprint")
    @Test
    void test() {
        //given
        Backlog backlog = backlogService.createBacklog();
        //when
        Optional<Sprint> sprint = sprintService.scheduleNewSprint(backlog.getId());
        //then
        assertThat(sprint).isPresent();
    }

    @DisplayName("commit backlog item to sprint")
    @Test
    void test1() {
        //given
        Backlog backlog = backlogService.createBacklog();
        Long backlogId = backlog.getId();
        backlogService.addItem("bug", "fix me", backlogId);
        Long itemId = backlogService.allItems(backlogId).get(0).getId();
        Long sprintId = sprintService.scheduleNewSprint(backlogId).get().getId();
        //when
        sprintService.commitBacklogItemToSprint(backlogId, itemId, sprintId);
        //then
        List<SprintItem> sprintItems = sprintService.allSprintItems(sprintId);
        assertThat(sprintItems).hasSize(1);
        assertThat(sprintItems.get(0))
                .hasFieldOrPropertyWithValue("title", "bug")
                .hasFieldOrPropertyWithValue("description", "fix me");
    }

    @DisplayName("assign committed sprint item to user")
    @Test
    void test2() {
        //given
        Backlog backlog = createBacklog();
        BacklogItem backlogItem = addAnyItemToBacklog(backlog);
        Sprint sprint = scheduleNewSprint(backlog);
        Long userId = addAnyUser();
        long itemId = backlogItem.getId();
        long sprintId = sprint.getId();
        sprintService.commitBacklogItemToSprint(backlog.getId(), itemId, sprintId);

        //when
        sprintService.assignItemToUser(itemId, sprintId, userId);

        //then
        assertThat(sprintService.allSprintItems(sprintId).get(0).assignedUser().get().getId()).isEqualTo(userId);
    }

    private Long addAnyUser() {
        return userRepository.save(new User()).getId();
    }

    private BacklogItem addAnyItemToBacklog(Backlog backlog) {
        backlogService.addItem("bug", "fix me", backlog.getId());
        BacklogItem backlogItem = backlogService.allItems(backlog.getId()).get(0);
        return backlogItem;
    }

    private Backlog createBacklog() {
        return backlogService.createBacklog();
    }

    private Sprint scheduleNewSprint(Backlog backlog) {
        return sprintService.scheduleNewSprint(backlog.getId()).get();
    }
}