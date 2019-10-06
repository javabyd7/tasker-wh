package pl.sda.scrum;

import pl.sda.task.model.TaskAlreadyAssignedException;
import pl.sda.task.model.User;
import pl.sda.task.model.UserBusyException;

import java.util.Optional;

public class SprintItem {
    private final Long id;
    private String title;
    private String description;
    private User user;
    private boolean finished;

    public SprintItem(Long id,String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void finish() {
        this.finished = true;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Optional<String> assignedUser() {
        return Optional.ofNullable(user).map(User::getName);
    }

    public Long getId() {
        return id;
    }

    public void assignToUser(User user) {
        if (user.isBusy()) {
            throw new UserBusyException();
        }
        if (this.user != null) {
            throw new TaskAlreadyAssignedException();
        }
        user.setBusy(true);
        this.user = user;
    }

    public boolean isFinished() {
        return finished;
    }
}
