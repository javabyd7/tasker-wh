package pl.sda.scrum.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class AssignItemToUserDto {
    private final Long idUser;

    @JsonCreator
    public AssignItemToUserDto(@JsonProperty("userId") Long idUser) {
        this.idUser = idUser;
    }
}
