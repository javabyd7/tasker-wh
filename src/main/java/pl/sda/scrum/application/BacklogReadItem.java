package pl.sda.scrum.application;

import lombok.Value;

@Value
public class BacklogReadItem {

    private final Long id;
    private final String titleAndDescription;


}
