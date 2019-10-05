package pl.sda.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.task.model.Task;
import pl.sda.task.mvc.MvcTasksController;
import pl.sda.task.service.TaskService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MvcTasksController.class)
public class MvcAddTaskControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    TaskService taskService;

    @DisplayName("When post on /mvc/addTask with task parameters then task will be created")
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
}
