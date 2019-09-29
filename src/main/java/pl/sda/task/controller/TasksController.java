package pl.sda.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.task.TaskRepository;
import pl.sda.task.model.Task;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private TaskRepository taskRepository;

    public TasksController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Iterable<Task> anything() {
        return taskRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody Task task) {
        taskRepository.save(task);
    }
}