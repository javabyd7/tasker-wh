package pl.sda.scrum.rest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.scrum.application.SprintService;
import pl.sda.scrum.model.Backlog;

@WebMvcTest(controllers = SprintRestController.class)
public class SprintRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private SprintService sprintService;

    @DisplayName("When POST on /api/scrum/sprints Then new sprint is scheduled")
    @Test
    void shceduleSprint() throws Exception {
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

}
