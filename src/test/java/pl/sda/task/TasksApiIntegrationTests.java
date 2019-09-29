package pl.sda.task;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class TasksApiIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("When GET on /api/tasks then status OK")
    public void test() throws Exception {
        //when
        mockMvc.perform(get("/api/tasks"))
                //then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When POST on /api/tasks with content task then new task is created")
    public void test1() throws Exception {
        //given
        String task = "{\"title\":\"title\",\"description\":\"description\"}";
        //when
        mockMvc.perform(
                post("/api/tasks")
                        .content(task)
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated()
                );
        mockMvc.perform(get("/api/tasks"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("title")))
                .andExpect(jsonPath("$[0].description", is("description")));
    }
}
