package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Room {
    private final String name;
    private final int rows;
    private final int cols;

    @Override
    public String toString() {
        return "Room " + name + " with " + rows * cols + " seats, " + rows + " rows and " + cols + " columns";
    }
}
