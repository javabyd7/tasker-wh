package pl.sda.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.sda.task.model.Task;
import pl.sda.task.model.User;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class TasksApiIntegrationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

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

    @Test
    @DisplayName("When PUT on /api/tasks/{id}/user then task is assigned to user")
    public void test2() throws Exception {
        //given
        Task task = createTask("{\"title\":\"title\",\"description\":\"description\"}");
        User user = createUser("{\"name\": \"goobar\"}");
        //when
        mockMvc.perform(put("/api/tasks/{id}/user", task.getId())
                .contentType(MediaType.TEXT_PLAIN).content(String.valueOf(user.getId())))
                //then
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/tasks/{id}", task.getId()))
                .andExpect(jsonPath("$.user.id", is(Math.toIntExact(user.getId()))));
    }

    private Task createTask(String task) throws Exception {
        return objectMapper.readValue(mockMvc.perform(
                post("/api/tasks")
                        .content(task)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(), Task.class);
    }

    private User createUser(String user) throws Exception {
        return objectMapper.readValue(mockMvc.perform(post("/api/users").content(user).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(), User.class);
    }
}