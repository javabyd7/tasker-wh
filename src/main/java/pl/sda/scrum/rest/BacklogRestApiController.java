package pl.sda.scrum.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.scrum.application.BacklogReadItem;
import pl.sda.scrum.application.BacklogService;

import java.util.Collection;
import java.util.List;
@RequestMapping("/api/scrum/backlogs")
@RestController
public class BacklogRestApiController {


    private BacklogService backlogService;

    public BacklogRestApiController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBacklog() {
        backlogService.createBacklog();
    }

    @PostMapping("/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBacklogItem( @PathVariable("id") Long backlogId,@RequestBody CreateBacklogItemDto dto){
        backlogService.addItem(dto.getTitle(),dto.getDescription(),backlogId);

    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BacklogReadItemDto getBacklogItems(@PathVariable("id") Long backlogId){
        return new BacklogReadItemDto(backlogService.allReadItems(backlogId));
    }

    @Value
    private class BacklogReadItemDto {
        private Collection<BacklogReadItem> backlogItems;
    }
}
