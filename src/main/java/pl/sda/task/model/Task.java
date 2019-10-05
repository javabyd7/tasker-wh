package pl.sda.task.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Optional;

@Data
@Entity
@EqualsAndHashCode(of = "id")
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @OneToOne
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void assignTo(User user) throws CannotAssignTaskException {
        if (user.isBusy()) {
            throw new UserBusyException();
        }
        if (this.user != null) {
            throw new TaskAlreadyAssignedException();
        }
        user.setBusy(true);
        this.user = user;
    }

    public Optional<User> assignedUser() {
        return Optional.ofNullable(user);
    }
}
