package pl.sda.task.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.task.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
