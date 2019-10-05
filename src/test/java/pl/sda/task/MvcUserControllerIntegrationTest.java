package pl.sda.task;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.task.model.User;
import pl.sda.task.repository.UserRepository;
import pl.sda.task.web.mvc.MvcUserController;
import pl.sda.task.web.rest.controller.UsersController;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MvcUserController.class)
public class MvcUserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserRepository userRepository;

    @DisplayName("When POST on /mvc/users with User model then user should be created")
    @Test
    public void test() throws Exception {
        //when
        mockMvc.perform(post("/mvc/users").param("name", "Andrzej"))
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mvc/users"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("When GET on /mvc/users then all Users should be returned")
    @Test
    public void test1() throws Exception {
        //given
        Collection<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        //when
        mockMvc.perform(get("/mvc/users"))
                //then
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", users))
                .andExpect(content().string(containsString("<title>Users</title>")));
    }
}