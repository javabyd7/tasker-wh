package pl.sda.scrum.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sda.scrum.application.BacklogService;

@RestController
public class BacklogRestApiController {


    private BacklogService backlogService;

    public BacklogRestApiController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @PostMapping("/api/scrum/backlogs")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBacklog() {
        backlogService.createBacklog();
    }

    @PostMapping("/api/scrum/backlogs/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBacklogItem( @PathVariable("id") Long backlogId,@RequestBody CreateBacklogItemDto dto){
        backlogService.addItem(dto.getTitle(),dto.getDescription(),backlogId);

    }
}
