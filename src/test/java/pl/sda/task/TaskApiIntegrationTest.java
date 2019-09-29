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
import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class TaskApiIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("When GET on /api/tasks then status is ok")
    public void test() throws Exception {
        //when
        mockMvc.perform(get("/api/task"))
                //then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When POST on /api/tasks with content task then new task is created")
    public void test1() throws Exception {
        //given
        String task = "{\"title\" :\"title\",\"description\" : \"description\"}";
        //when
        mockMvc.perform(post("/api/tasks")
                .content(task)
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated());
        mockMvc.perform(get("/api/tasks"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("title")))
                .andExpect(jsonPath("$[0].description", is("description")));
    }

    @Test
    @DisplayName("When PUT on /api/task/{id}/user then task is assignd to user")
    public void test2() throws Exception {
        //given
        String task = "{\"title\" :\"title\",\"description\" : \"description\"}";
        Task createdTask = createTask(task);
        String user = "{\"name\": \"goobar\"}";
        User createdUser = createUser(user);
        //when
        mockMvc.perform(put("/api/task/{id}/user", createdTask.getId())
                .contentType(MediaType.TEXT_PLAIN)
                .content(String.valueOf(createdUser.getId())))
                //then
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/tasks/{id}", createdTask.getId()))
                .andExpect(jsonPath("$.user.id", Matchers.is(createdUser.getId())));
    }

    private Task createTask(String task) throws Exception {
        return objectMapper.readValue(
        mockMvc.perform(post("/api/tasks")
                .content(task)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(), Task.class);
    }

    private User createUser(String user) throws Exception {
        return objectMapper.readValue(
        mockMvc.perform(post("/api/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(), User.class);
    }
}
