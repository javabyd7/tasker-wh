package pl.sda.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {
	private SecurityUserRepository userRepository;

	public RepositoryUserDetailsService(SecurityUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
			.map(user -> new User(user.getUsername(), user
				.getPassword(), Collections
				.singleton(new SimpleGrantedAuthority("ROLE_" + user
					.getRole()))))
			.orElseThrow(() -> new UsernameNotFoundException(String
				.format("User %s doesn't exist.", username)));
	}
}
