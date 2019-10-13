package pl.sda.scrum.rest;

import lombok.Value;

@Value
public class SprintReadItem {
    private final Long id;
    private final String title;
    private final String description;
    private final Long user;
}
