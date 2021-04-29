package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Screening {

    private final Movie movie;
    private final Room room;
    private final Date startTime;

    @Override
    public String toString() {
        return movie + ", screened in room " + room.getName() + ", at " + startTime;
    }
}
