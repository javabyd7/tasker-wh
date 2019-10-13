package pl.sda.scrum.security;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.scrum.application.BacklogService;
import pl.sda.scrum.rest.BacklogRestApiController;

@WebMvcTest(controllers = BacklogRestApiController.class)
@MockBean(classes = BacklogService.class)

public class SecurityConfigurationTest {
    @Autowired
    MockMvc mockMvc;




    @DisplayName("Get on api/scrum/backlogs then status 401")
    @Test
    void test
            () throws Exception {
        // given
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scrum/backlogs"))
        // then
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @DisplayName("Get on same then status 201")
    @Test
    void test1() throws Exception
    {
        // given
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/scrum/backlogs")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("wojti","secret")))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // then

    }
}
