package pl.sda.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.task.TaskRepository;
import pl.sda.task.UserRepository;
import pl.sda.task.model.Task;
import pl.sda.task.model.User;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TasksController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Task> anything() {
        return taskRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        Task createdTask = taskRepository.save(task);
        return createdTask;
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") String taskId) {
        return taskRepository.findById(Long.valueOf(taskId));
    }

    @PutMapping("/{id}/user")
    public void assignUser(@RequestBody String userId, @PathVariable("id") String taskId) {
        Optional<User> optionalUser = userRepository.findById(Long.valueOf(userId));
        Optional<Task> optionalTask = taskRepository.findById(Long.valueOf(taskId));
        optionalTask.ifPresent(t -> {
            optionalUser.ifPresent(u -> {
                t.setUser(u);
                taskRepository.save(t);
            });
        });
    }
}