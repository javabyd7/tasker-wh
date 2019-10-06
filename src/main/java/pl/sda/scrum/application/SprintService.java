package pl.sda.scrum.application;

import org.springframework.stereotype.Service;
import pl.sda.scrum.model.Backlog;
import pl.sda.scrum.model.BacklogItem;
import pl.sda.scrum.model.Sprint;
import pl.sda.scrum.model.SprintItem;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    SprintRepository sprintRepository;
    BacklogRepository backlogRepository;

    public SprintService(SprintRepository sprintRepository, BacklogRepository backlogRepository) {
        this.sprintRepository = sprintRepository;
        this.backlogRepository = backlogRepository;
    }

    public Optional<Sprint> scheduleNewSprint(Long backlogId) {
        return backlogRepository.findById(backlogId)
                .map(backlog -> {
                    Sprint sprint = backlog.scheduleSprint();
                    return sprintRepository.save(sprint);
                });
    }

    public void commitBacklogItemToSprint(Long backlogId, Long itemId, Long sprintId) {
        backlogRepository.findById(backlogId).ifPresent(backlog -> {
            backlog.findItemById(itemId).ifPresent(backlogItem -> {
                sprintRepository.findById(sprintId).ifPresent(sprint -> {
                    sprint.commitBacklogItem(backlogItem);
                    sprintRepository.save(sprint);
                });
            });
        });
    }

    public List<SprintItem> allSprintItems(Long sprintId) {
        return sprintRepository.findById(sprintId)
                .map(Sprint::getSprintItems)
                .orElse(Collections.emptyList());
    }
}
