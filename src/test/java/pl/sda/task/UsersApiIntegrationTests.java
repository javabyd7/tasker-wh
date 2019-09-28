package pl.sda.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When invoke get on /api/users then return 200 status")
    public void test() throws Exception {
        // when
        mockMvc.perform(get("/api/users"))

                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When invoke POST on /api/users with user data then user is created")
    public void test1() throws Exception {
        //given
        String user = "{\"name\": \"goobar\"}";

        //when
        mockMvc.perform(post("/api/users").content(user).contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isCreated());
        mockMvc.perform(get("/api/users"))
                .andExpect(jsonPath("$.name", is("goobar")));
    }
}
