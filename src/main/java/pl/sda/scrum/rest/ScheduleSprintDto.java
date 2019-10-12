package pl.sda.scrum.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ScheduleSprintDto {
    private long backlogId;

    @JsonCreator
    public ScheduleSprintDto(@JsonProperty("backlogId") long backlogId) {
        this.backlogId = backlogId;
    }
}
