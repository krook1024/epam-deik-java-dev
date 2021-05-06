package com.epam.training.ticketservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoomTest {

    @Test
    void testToString() {
        // Given
        Room underTest = new Room("Test", 4, 4);

        // When
        String s = underTest.toString();

        // Then
        assertEquals("Room Test with 16 seats, 4 rows and 4 columns", s);
    }
}