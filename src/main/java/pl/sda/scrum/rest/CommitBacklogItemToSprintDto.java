package pl.sda.scrum.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CommitBacklogItemToSprintDto {
    private final Long backlogId;
    private final Long backlogItemId;

    @JsonCreator
    public CommitBacklogItemToSprintDto(
            @JsonProperty("backlogId") Long backlogId,
            @JsonProperty("backlogItemId") Long backlogItemId) {
        this.backlogId = backlogId;
        this.backlogItemId = backlogItemId;
    }
}
