package pl.sda.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sda.task.model.Task;
import pl.sda.task.model.TaskAlreadyAssignedException;
import pl.sda.task.model.User;
import pl.sda.task.model.UserBusyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class AssignTaskToUserTest {

    @Test
    @DisplayName("Given unassigned task fixme and not busy user Janek " +
            "when assign fixme to janek " +
            "then janek is working on fixme")
    void test() {
        //given
        User janek = notBusyUserWithName("Janek");
        Task fixme = unassingnedTaskWithTitle("fixme");
        //when
        fixme.assignTo(janek);
        //then
        assertThat(janek.isBusy()).isTrue();
        assertThat(fixme.assignedUser()).hasValue(janek);
    }

    @Test
    @DisplayName("Given unassigned task fixme and busy Janek " +
            "when assign fixme to busy janek " +
            "then assign fails")
    void test1() {
        //given
        User janek = busyUserWithName("Janek");
        Task fixme = unassingnedTaskWithTitle("fixme");
        //when
        UserBusyException exception = catchThrowableOfType(() -> fixme.assignTo(janek), UserBusyException.class);
        // then
        assertThat(exception).isNotNull();
        assertThat(janek.isBusy()).isTrue();
        assertThat(fixme.assignedUser()).isEmpty();
    }

    @Test
    @DisplayName("Given assigned task fixme and not busy Janek " +
            "when assign fixme to janek " +
            "then assign fails")
    void test2() {
        //given
        User janek = notBusyUserWithNameAndId("Janek", 6L);
        Task fixme = assignedTaskWithTitleToUser("fixme",notBusyUserWithNameAndId("Andrzej",10L));
        //when
        TaskAlreadyAssignedException exception = catchThrowableOfType(() -> fixme.assignTo(janek), TaskAlreadyAssignedException.class);
        //then
        assertThat(exception).isNotNull();
        assertThat(janek.isBusy()).isFalse();
        assertThat(fixme.assignedUser().get()).isNotEqualTo(janek);
    }

    private Task assignedTaskWithTitleToUser(String title, User user) {
        Task task = new Task();
        task.setTitle(title);
        task.setUser(user);
        return task;
    }

    private Task unassingnedTaskWithTitle(String title) {
        Task task = new Task();
        task.setTitle(title);
        task.setUser(null);
        return task;
    }

    private User notBusyUserWithName(String name) {
        User user = new User();
        user.setName(name);
        user.setBusy(false);
        return user;
    }

    private User notBusyUserWithNameAndId(String name, long id) {
        User user = new User();
        user.setName(name);
        user.setId(id);
        user.setBusy(false);
        return user;
    }

    private User busyUserWithName(String name) {
        User user = new User();
        user.setName(name);
        user.setBusy(true);
        return user;
    }
}
