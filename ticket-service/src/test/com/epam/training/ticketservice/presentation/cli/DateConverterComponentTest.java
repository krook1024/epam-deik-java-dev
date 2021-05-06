package com.epam.training.ticketservice.presentation.cli;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DateConverterComponentTest {

    @Test
    void assertConvertReturnsNullOnGibberishInput() {
        // Given
        DateConverterComponent underTest = new DateConverterComponent();

        // When, Then
        assertNull(underTest.convert("asdal13k1nlkj12"));
    }
}