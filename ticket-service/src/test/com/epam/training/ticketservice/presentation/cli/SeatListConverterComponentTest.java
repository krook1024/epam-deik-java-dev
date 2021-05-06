package com.epam.training.ticketservice.presentation.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.training.ticketservice.domain.Seat;
import java.util.List;
import org.junit.jupiter.api.Test;

class SeatListConverterComponentTest {

    @Test
    void testConvertGivesCorrectOutput() {
        // Given
        SeatListConverterComponent underTest = new SeatListConverterComponent();
        String in = "1,1 2,2 3,3";

        // When
        List<Seat> out = underTest.convert(in);

        // Then
        assertEquals(List.of(
            new Seat(1, 1),
            new Seat(2, 2),
            new Seat(3, 3)
        ), out);
    }
}