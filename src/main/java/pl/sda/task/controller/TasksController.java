package pl.sda.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.task.model.Task;
import pl.sda.task.model.User;
import pl.sda.task.service.TaskService;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Iterable<Task> anything() {
        return taskService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        Task createdTask = taskService.create(task);
        return createdTask;
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") String taskId) {
        return taskService.findById(Long.valueOf(taskId));
    }

    @PutMapping("/{id}/user")
    public void assignUser(@PathVariable("id") String taskId, @RequestBody String userId) {
        taskService.assignTaskToUser(Long.valueOf(taskId), Long.valueOf(userId));
    }
}