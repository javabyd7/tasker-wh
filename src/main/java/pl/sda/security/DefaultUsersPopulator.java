package pl.sda.security;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultUsersPopulator implements ApplicationRunner {
	private SecurityUserRepository repository;
	private PasswordEncoder passwordEncoder;

	public DefaultUsersPopulator(SecurityUserRepository repository,
				     PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		UUID id = UUID.randomUUID();
		if (repository.existsById(id)) {
			return;
		}
		repository
			.save(new SecurityUser(id, "wojti", "USER",
				passwordEncoder
				.encode("secret")));
	}
}
