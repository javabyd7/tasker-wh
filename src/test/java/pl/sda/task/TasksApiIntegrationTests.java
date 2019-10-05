package pl.sda.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.task.web.rest.controller.TasksController;
import pl.sda.task.model.Task;
import pl.sda.task.service.TaskService;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TasksController.class)
public class TasksApiIntegrationTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private TaskService taskService;

    @Test
    @DisplayName("When GET on /api/tasks then status OK and return all tasks")
    public void test() throws Exception {
        // given
        Task anyTask = taskWithTitleAndDescription("task title", "task description");
        Mockito.when(taskService.findAll()).thenReturn(Arrays.asList(anyTask));

        //when
        mockMvc.perform(get("/api/tasks"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("task title")))
                .andExpect(jsonPath("$[0].description", is("task description")));
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
        Mockito.verify(taskService, Mockito.times(1)).create(ArgumentMatchers.any(Task.class));
    }

    @Test
    @DisplayName("When PUT on /api/tasks/{id}/user then task is assigned to user")
    public void test2() throws Exception {
        //given
        long userId = 1;
        long taskId = 2;

        //when
        mockMvc.perform(put("/api/tasks/{id}/user", taskId)
                .contentType(MediaType.TEXT_PLAIN).content(String.valueOf(userId)))
                //then
                .andExpect(status().isOk());
        Mockito.verify(taskService, Mockito.times(1)).assignTaskToUser(taskId, userId);
    }

    private Task taskWithTitleAndDescription(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        return task;
    }

}