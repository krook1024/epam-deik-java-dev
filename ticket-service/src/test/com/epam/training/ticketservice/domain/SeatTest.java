package com.epam.training.ticketservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    void testToString() {
        // Given
        Seat seat = new Seat(1, 1);

        // When
        String string = seat.toString();

        // Then
        assertEquals("(1,1)", string);
    }
}