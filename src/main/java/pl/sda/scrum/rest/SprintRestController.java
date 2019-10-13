package pl.sda.scrum.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.scrum.application.SprintService;

@RestController
@RequestMapping("/api/scrum/sprints")
public class SprintRestController {


    SprintService sprintService;

    public SprintRestController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void scheduleSprint(@RequestBody ScheduleSprintDto dto) {
        sprintService.scheduleNewSprint(dto.getBacklogId());
    }
    @PostMapping("/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void commitBacklogItemToSprint(@RequestBody CommitBacklogItemToSprintDto dto,
                                          @PathVariable("id") Long sprintId) {
        sprintService.commitBacklogItemToSprint(dto.getBacklogId(),dto.getBacklogItemId(),sprintId);
    }
    @PutMapping("/{sprintId}/items/{itemId}/user")
    @ResponseStatus(HttpStatus.OK)
    public void assignItemToUser(@RequestBody AssignItemToUserDto dto,
                                 @PathVariable("sprintId") Long sprintId,
                                 @PathVariable("itemId") Long itemId){
        sprintService.assignItemToUser(itemId,sprintId, dto.getIdUser());

    }
    @PutMapping("/{sprintId}/confirmed")
    @ResponseStatus(HttpStatus.OK)
    public void confirmSprint(@PathVariable("sprintId") Long sprintId){
        sprintService.confirmSprint(sprintId);
    }

    @PutMapping("/{sprintId}/items/{itemId}/finished")
    @ResponseStatus(HttpStatus.OK)
    public void markItemAsFinished(@PathVariable("sprintId") Long sprintId,
                                   @PathVariable("itemId") Long itemId){
        sprintService.markItemAsFinished(itemId,sprintId);
    }
}
