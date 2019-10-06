package pl.sda.scrum.application;


import org.springframework.stereotype.Service;
import pl.sda.scrum.model.Backlog;
import pl.sda.scrum.model.BacklogItem;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BacklogService {

    BacklogRepository backlogRepository;

    public BacklogService(BacklogRepository backlogRepository) {
        this.backlogRepository = backlogRepository;
    }

    public Backlog createBacklog() {
        return backlogRepository.save(new Backlog());
    }

    public void addItem(String title, String description, Long backlogId) {
        backlogRepository.findById(backlogId).ifPresent(backlog -> {
            backlog.add(new BacklogItem(title, description));
            backlogRepository.save(backlog);
        });
    }

    public List<BacklogItem> allItems(Long id) {
        return backlogRepository.findById(id).map(Backlog::getBacklogItems).orElse(Collections.emptyList());
    }
}