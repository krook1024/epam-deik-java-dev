package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Seat {

    private final int row;
    private final int col;

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
