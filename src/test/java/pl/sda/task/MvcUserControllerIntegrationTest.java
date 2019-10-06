package pl.sda.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.common.user.User;
import pl.sda.common.user.UserRepository;
import pl.sda.common.user.MvcUserController;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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