package com.epam.training.ticketservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MovieTest {

    @Test
    void testToString() {
        // Given
        Movie underTest = new Movie("Title", "Genre", 69);

        // When
        String s = underTest.toString();

        // Then
        assertEquals("Title (Genre, 69 minutes)", s);
    }
}