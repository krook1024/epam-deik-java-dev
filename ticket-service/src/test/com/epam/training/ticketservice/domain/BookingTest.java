package com.epam.training.ticketservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.training.ticketservice.presentation.cli.DateConverterComponent;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookingTest {

    DateConverterComponent dateConverterComponent;

    @BeforeEach
    void setUp() {
        dateConverterComponent = new DateConverterComponent();
    }

    @Test
    void testToString() {
        // Given
        Date startTime = dateConverterComponent.convert("2020-01-01 10:00");
        Screening screening = new Screening(
            new Movie("Movie", "Genre", 420),
            new Room("Room", 4, 4),
            startTime
        );
        User user = new User("Johnny Test", "secret", false);
        List<Seat> seats = List.of(
            new Seat(1, 1),
            new Seat(1, 2),
            new Seat(1, 3)
        );
        Booking underTest = new Booking(
            screening,
            user,
            seats
        );

        // When
        String string = underTest.toString();

        // Then
        assertEquals("Seats (1,1), (1,2), (1,3) on Movie in room Room at 2020-01-01 10:00 for 4500 HUF",
            string);
    }
}