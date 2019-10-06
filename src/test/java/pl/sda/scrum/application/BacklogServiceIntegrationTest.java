package pl.sda.scrum.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.sda.scrum.model.Backlog;
import pl.sda.scrum.model.BacklogItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BacklogServiceIntegrationTest {


    @Autowired
    private BacklogService backlogService;

    @DisplayName("When create backlog then backlog is created and returned")
    @Test
    void test() {
        assertThat(backlogService.createBacklog()).isNotNull();
    }

    @DisplayName("add new item to empty backlog")
    @Test
    void test1() {
        //given
        Backlog backlog = backlogService.createBacklog();
        Long backlogId = backlog.getId();
        //when
        backlogService.addItem("bug", "fix me", backlogId);
        //then
        List<BacklogItem> backlogItems = backlogService.allItems(backlogId);
        assertThat(backlogItems).hasSize(1);
        assertThat(backlogItems.get(0))
                .hasFieldOrPropertyWithValue("title", "bug")
                .hasFieldOrPropertyWithValue("description", "fix me");
    }
}