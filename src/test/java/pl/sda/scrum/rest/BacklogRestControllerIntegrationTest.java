package pl.sda.scrum.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.scrum.application.BacklogService;

@WebMvcTest(controllers = BacklogRestApiController.class)
public class BacklogRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BacklogService backlogService;

    @DisplayName("When POST on /api/scrum/backlogs Then new backlog is created")
    @Test
    void baclkogsPost() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/scrum/backlogs"))
                //then
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(backlogService).createBacklog();
    }

    @DisplayName("When POST on /api/scrum/backlogs/{id}/items with backlog item THEN new item is created")
    @Test
    void postBacklogItems() throws Exception {
        //given
        //language=JSON
        String createBacklogDtoJson = "{" +
                "\"title\": \"bug\"," +
                " \"description\" : \"fixme\"" +
                "}";
        //when
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/scrum/backlogs/{id}/items",1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createBacklogDtoJson))
        //then
                    .andExpect(MockMvcResultMatchers.status().isCreated());
            Mockito.verify(backlogService).addItem("bug","fixme",1L);
    }
}
