package com.epam.training.ticketservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.training.ticketservice.presentation.cli.DateConverterComponent;
import java.util.Date;
import org.junit.jupiter.api.Test;

class ScreeningTest {

    @Test
    void testToString() {
        // Given
        Date startTime = new DateConverterComponent().convert("2021-01-01 10:00");
        Movie movie = new Movie("Title", "Genre", 69);
        Room room = new Room("Room", 4, 4);
        Screening underTest = new Screening(movie, room, startTime);

        // When
        String s = underTest.toString();

        // Then
        assertEquals("Title (Genre, 69 minutes), screened in room Room, at 2021-01-01 10:00",
            s);
    }
}