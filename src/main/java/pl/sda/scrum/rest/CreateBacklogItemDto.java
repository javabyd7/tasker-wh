package pl.sda.scrum.rest;

import lombok.Value;

@Value
public class CreateBacklogItemDto {

    private final String title;
    private final String description;


}
