package pl.sda.task.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.task.model.Task;
import pl.sda.task.service.TaskService;

@Controller
@RequestMapping("/mvc")
public class MvcTasksController {

    private final TaskService taskService;

    public MvcTasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/addTask")
    public String addTask(Task task) {
        taskService.create(task);
        return "redirect:/mvc/tasks";
    }
}