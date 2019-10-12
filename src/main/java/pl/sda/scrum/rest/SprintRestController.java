package pl.sda.scrum.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.scrum.application.SprintService;

@RestController
public class SprintRestController {


    SprintService sprintService;

    public SprintRestController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping("/api/scrum/sprints")
    @ResponseStatus(HttpStatus.CREATED)
    public void scheduleSprint(@RequestBody ScheduleSprintDto dto) {
        sprintService.scheduleNewSprint(dto.getBacklogId());
    }
}
