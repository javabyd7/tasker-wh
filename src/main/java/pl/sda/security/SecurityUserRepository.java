package pl.sda.security;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SecurityUserRepository extends CrudRepository<SecurityUser,
	UUID> {
	Optional<SecurityUser> findByUsername(String username);
}
