package pl.sda.scrum.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import pl.sda.scrum.model.Backlog;
import pl.sda.scrum.model.Sprint;
import pl.sda.scrum.model.SprintItem;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SprintServiceIntegrationTest {

    @Autowired
    SprintService sprintService;
    @Autowired
    BacklogService backlogService;

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
}