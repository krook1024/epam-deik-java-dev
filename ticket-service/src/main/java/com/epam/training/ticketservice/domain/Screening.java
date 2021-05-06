package com.epam.training.ticketservice.domain;

import com.epam.training.ticketservice.presentation.cli.DateConverterComponent;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Screening {

    private final Movie movie;
    private final Room room;
    private final Date startTime;

    @Override
    public String toString() {
        return movie + ", screened in room " + room.getName() + ", at " + new DateConverterComponent()
            .convertBack(startTime);
    }
}
