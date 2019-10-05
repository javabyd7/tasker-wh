package pl.sda.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.task.TaskRepository;
import pl.sda.task.UserRepository;
import pl.sda.task.model.Task;
import pl.sda.task.model.User;

import java.util.Optional;

@Service
public class TaskService {

    final
    TaskRepository taskRepository;
    final
    UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void assignTaskToUser(Long taskId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        optionalTask.ifPresent(t -> {
            optionalUser.ifPresent(u -> {
                t.assignTo(u);
                taskRepository.save(t);
            });
        });
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }
}
