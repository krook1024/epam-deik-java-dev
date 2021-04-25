package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class Screening {
    private Movie movie;
    private Room room;
    private Date start;
}
