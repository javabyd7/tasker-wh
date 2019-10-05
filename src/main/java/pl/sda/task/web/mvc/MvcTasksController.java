package pl.sda.task.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.task.model.CannotAssignTaskException;
import pl.sda.task.model.Task;
import pl.sda.task.service.TaskService;

@Controller
@RequestMapping("/mvc")
public class MvcTasksController {

    private final TaskService taskService;

    public MvcTasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/addTask")
    public String getAddTask() {
        return "createTask-form";
    }

    @PostMapping("/addTask")
    public String addTask(Task task) {
        taskService.create(task);
        return "redirect:/mvc/tasks";
    }

    @GetMapping("/tasks")
    public String getTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks-form";
    }

    @PostMapping("/assignTask")
    public String assignTaskToUser(@RequestParam("userId") Long userId, @RequestParam("taskId") Long taskId) {
        try {
            taskService.assignTaskToUser(taskId, userId);
        } catch (CannotAssignTaskException e) {
            return "redirect:/mvc/tasks?errors=" + e.getMessage();
        }
        return "redirect:/mvc/tasks";
    }
}