package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Movie {

    private final String title;
    private final String genre;
    private final int length;

    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + " minutes)";
    }
}
