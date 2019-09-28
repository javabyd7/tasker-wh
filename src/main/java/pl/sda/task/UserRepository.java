package pl.sda.task;

import org.springframework.data.repository.CrudRepository;
import pl.sda.task.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
