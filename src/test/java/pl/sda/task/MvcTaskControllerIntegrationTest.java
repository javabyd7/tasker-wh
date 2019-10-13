package pl.sda.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.task.model.CannotAssignTaskException;
import pl.sda.task.model.Task;
import pl.sda.task.web.mvc.MvcTasksController;
import pl.sda.task.service.TaskService;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser
@WebMvcTest(MvcTasksController.class)
public class MvcTaskControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    TaskService taskService;

    @DisplayName("When POST on /mvc/addTask with task parameters then task will be created")
    @Test
    void test() throws Exception {
        //when
        mockMvc.perform(post("/mvc/addTask")
                .param("title", "fixme")
                .param("description", "fix this bug"))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mvc/tasks"));
        verify(taskService, times(1)).create(any(Task.class));
    }

    @DisplayName("When GET on /mvc/tasks then all tasks should be returned")
    @Test
    void test1() throws Exception {
        //given
        Collection<Task> tasks = Arrays.asList(new Task());
        when(taskService.findAll()).thenReturn(tasks);
        //when
        mockMvc.perform(get("/mvc/tasks"))
                //then
                .andExpect(status().isOk())
                .andExpect(view().name("tasks-form"))
                .andExpect(model().attribute("tasks", tasks))
                .andExpect(content().string(containsString("<title>Tasks</title>")));
    }

    @DisplayName("When POST on /mvc/assignTask with taks id and user id then task is assigned and status is ok")
    @Test
    void test2() throws Exception {
        //when
        mockMvc.perform(post("/mvc/assignTask")
                .param("userId", String.valueOf(1))
                .param("taskId", String.valueOf(2)))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mvc/tasks"));

        verify(taskService).assignTaskToUser(2L, 1L);
    }

    @DisplayName("When POST on /mvc/assignTask fails then error should be show")
    @Test
    void test3() throws Exception {
        //given
        CannotAssignTaskException exception = new CannotAssignTaskException("Cannot assign task because");
        doThrow(exception).when(taskService).assignTaskToUser(2L, 1L);
        //when
        mockMvc.perform(post("/mvc/assignTask")
                .param("userId", String.valueOf(1))
                .param("taskId", String.valueOf(2)))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mvc/tasks?errors=" + exception.getMessage()));
    }
}