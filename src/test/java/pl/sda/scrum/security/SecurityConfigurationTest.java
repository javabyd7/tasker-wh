package pl.sda.scrum.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.scrum.application.BacklogService;

@SpringBootTest
@MockBean(classes = BacklogService.class)
@AutoConfigureMockMvc
public class SecurityConfigurationTest {
	@Autowired
	MockMvc mockMvc;

	@DisplayName("Get on api/scrum/backlogs then status 401")
	@Test
	void test() throws Exception {
		// when
		mockMvc.perform(MockMvcRequestBuilders
			.get("/api/scrum/backlogs"))
			// then
			.andExpect(MockMvcResultMatchers.status()
				.isUnauthorized());
	}

	@DisplayName("Get on same then status 201")
	@Test
	void test1() throws Exception {
		// when
		mockMvc.perform(MockMvcRequestBuilders
			.post("/api/scrum/backlogs")
			.with(SecurityMockMvcRequestPostProcessors
				.httpBasic("wojti", "secret")))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		// then

	}
}
