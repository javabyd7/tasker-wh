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
public class TaskController {


    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Task> anything(){
        return taskRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task){

        return taskRepository.save(task);
    }

    @PutMapping("/{id}/user")
    public void assignUser(@RequestBody String userId, @PathVariable("id") String taskId){
        Optional<Task> task = taskRepository.findById(Long.valueOf(taskId));
        Optional<User> user = userRepository.findById(Long.valueOf(userId))
                .flatMap(u -> task).ifPresent(Task::setUser);


    }
}
