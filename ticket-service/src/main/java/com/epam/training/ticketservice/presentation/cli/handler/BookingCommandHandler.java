package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.service.BookingService;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Date;
import java.util.List;

@ShellComponent
@ShellCommandGroup("Booking commands")
public class BookingCommandHandler {

    BookingService bookingService;

    public BookingCommandHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ShellMethod(key = "book", value = "Book a ticket to a screening")
    public void book(String movieTitle, String roomName, Date startTime, List<Seat> seats) {
        try {
            bookingService.saveBooking(movieTitle, roomName, startTime, seats);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
