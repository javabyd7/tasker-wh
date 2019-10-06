package pl.sda.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.sda.task.model.Task;
import pl.sda.common.user.User;
import pl.sda.task.repository.TaskRepository;
import pl.sda.common.user.UserRepository;
import pl.sda.task.service.TaskService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("given user Janek and task FixMe, when assign FixMe to Janek, then Janek begins working on FixMe (if not lazy)")
    @Test
    void assign() {
        // given
        Task fixMe = taskWithTitle("FixMe");
        Long taskId = taskRepository.save(fixMe).getId();
        User janek = userWithName("Janek");
        Long userId = userRepository.save(janek).getId();

        // when
        taskService.assignTaskToUser(taskId, userId);

        // then
        Task task = taskService.findById(taskId).get();
        assertThat(task.getUser()).isEqualTo(janek);
    }

    private User userWithName(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    private Task taskWithTitle(String title) {
        Task task = new Task();
        task.setTitle(title);
        return task;
    }
}
