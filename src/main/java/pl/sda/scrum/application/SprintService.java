package pl.sda.scrum.application;

import org.springframework.stereotype.Service;
import pl.sda.common.user.UserRepository;
import pl.sda.scrum.model.Sprint;
import pl.sda.scrum.model.SprintItem;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    private SprintRepository sprintRepository;
    private BacklogRepository backlogRepository;
    private UserRepository userRepository;

    public SprintService(SprintRepository sprintRepository, BacklogRepository backlogRepository, UserRepository userRepository) {
        this.sprintRepository = sprintRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
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

    public void assignItemToUser(long itemId, long sprintId, long userId) {
        sprintRepository.findById(sprintId).ifPresent(sprint -> {
            userRepository.findById(userId).ifPresent(user -> {
                sprint.assignItemToUser(itemId, user);
                sprintRepository.save(sprint);
            });
        });
    }

    public void confirmSprint(long sprintId) {
        sprintRepository.findById(sprintId).ifPresent(sprint -> {
            sprint.confirm();
            sprintRepository.save(sprint);
        });
    }

    public void markItemAsFinished(Long itemId, Long sprintId) {
        sprintRepository.findById(sprintId).ifPresent(sprint -> {
            // we pass null as owner, because markItemAsFinished doesn't use owner argument yet (however, it should change in the nearest future)
            sprint.markItemAsFinished(itemId, null);
            sprintRepository.save(sprint);
        });
    }
}