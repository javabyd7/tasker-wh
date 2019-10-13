package pl.sda.scrum.rest;


import org.assertj.core.api.Fail;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.scrum.application.SprintService;
import pl.sda.scrum.model.Backlog;
import pl.sda.scrum.model.SprintItem;

import java.util.Collections;
import java.util.List;
@WithMockUser
@WebMvcTest(controllers = SprintRestController.class)
public class SprintRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private SprintService sprintService;


    @DisplayName("When POST on /api/scrum/sprints Then new sprint is scheduled")
    @Test
    void shceduleSprintTest() throws Exception {
        //given
        String scheduleSprintDto = "{ " +
                "\"backlogId\" : 1" +
                "}";
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/scrum/sprints")
                .contentType(MediaType.APPLICATION_JSON).content(scheduleSprintDto))
                //then
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(sprintService).scheduleNewSprint(1L);
    }

    @DisplayName("When POST on /api/scrum/sprints/{id}/items Then backlog item is commited to sprint")
    @Test
    void commitBacklogItemToSprintTest() throws Exception {
        // given
        //language=JSON
        String content = "{" +
                "\"backlogId\" : 2," +
                "\"backlogItemId\": 3" +
                "}";
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/scrum/sprints/{id}/items", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                // then
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(sprintService).commitBacklogItemToSprint(2L, 3L, 1L);
    }

    @DisplayName("When PUT on /api/scrum/sprints/{sprintId}/items/{itemId}/user Then item is assigned to user")
    @Test
    void asignItemToUserTest() throws Exception {
        // given
        String asignContent = "{\n" +
                "  \"userId\"\n" +
                ": 3" +
                "}";
        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/scrum/sprints/{sprintId}/items/{itemId}/user", 1l, 2L)
                .contentType(MediaType.APPLICATION_JSON).content(asignContent))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(sprintService).assignItemToUser(2L, 1L, 3L);
    }

    @DisplayName("When PUT on /api/scrum/sprints/{sprintId}/confirmed Then sprint is confirmed")
    @Test
    void confirmSprintTest() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/scrum/sprints/{sprintId}/confirmed", 1L))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(sprintService).confirmSprint(1L);
    }

    @DisplayName("When PUT on /api/scrum/sprints/{sprintId}/items/{itemId}/finished Then sprint Item is finished")
    @Test
    void markItemAsFinishedTest() throws Exception
    {
        // given
        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/scrum/sprints/{sprintId}/items/{itemId}/finished",1L,2L))
        // then
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(sprintService).markItemAsFinished(2L,1L);
    }

    @DisplayName("When GET on /api/scrum/sprints/{id} Then return all sprint items ")
    @Test
    void test() throws Exception
    {
        // given
        List<SprintReadItem> sprintItems = Collections.singletonList(
                new SprintReadItem(2L,"bug", "fixme",3L));

        Mockito.when(sprintService.allReadSprintItems(1L)).thenReturn(sprintItems);
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scrum/sprints/{id}",1L))
        // then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("bug")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("fixme")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user", Matchers.is(3)));
    }
}
