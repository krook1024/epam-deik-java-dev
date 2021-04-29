package com.epam.training.ticketservice.presentation.cli;

import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketServicePromptProviderTest {

    TicketServicePromptProvider underTest;

    @Test
    void getPrompt() {
        // Given
        underTest = new TicketServicePromptProvider();

        // When
        AttributedString attributedString = underTest.getPrompt();

        // Then
        assertEquals(attributedString, new AttributedString("Ticket service>"));
    }
}