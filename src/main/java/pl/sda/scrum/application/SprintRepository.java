package pl.sda.scrum.application;

import org.springframework.data.repository.CrudRepository;
import pl.sda.scrum.model.Sprint;

public interface SprintRepository extends CrudRepository<Sprint, Long> {
}
