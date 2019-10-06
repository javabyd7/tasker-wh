package pl.sda.scrum.model;

import lombok.NoArgsConstructor;
import pl.sda.task.model.TaskAlreadyAssignedException;
import pl.sda.common.user.User;
import pl.sda.common.user.UserBusyException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Optional;

@Entity
@NoArgsConstructor
public class SprintItem {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @OneToOne
    private User user;
    private boolean finished;

    public SprintItem(Long id, String title, String description) {
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