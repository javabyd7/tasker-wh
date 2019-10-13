package pl.sda.scrum.application;


import org.springframework.stereotype.Service;
import pl.sda.scrum.model.Backlog;
import pl.sda.scrum.model.BacklogItem;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            BacklogItem backlogItem = new BacklogItem(title, description);
            backlog.add(backlogItem);
            backlogRepository.save(backlog);
        });
    }

    public List<BacklogItem> allItems(Long id) {
        return backlogRepository.findById(id).map(Backlog::getBacklogItems).orElse(Collections.emptyList());
    }

    public List<BacklogReadItem> allReadItems(Long backlogId) {
        return allItems(backlogId).stream().map(backlogItem ->
                new BacklogReadItem(backlogItem.getId(),
                        backlogItem.getTitle() + " " + backlogItem.getDescription())
        ).collect(Collectors.toList());
    }
}