package pl.sda.scrum.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pl.sda.scrum.model.Backlog;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {
}
