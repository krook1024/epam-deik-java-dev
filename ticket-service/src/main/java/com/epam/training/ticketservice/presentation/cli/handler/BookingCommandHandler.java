package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.service.BookingService;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Date;
import java.util.Iterator;
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
            Booking booking = bookingService.saveBooking(movieTitle,
                    roomName,
                    startTime,
                    seats);

            StringBuilder sb = mapToString(seats);
            System.out.println(sb);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private StringBuilder mapToString(List<Seat> seats) {
        StringBuilder sb = new StringBuilder("Seats booked: ");
        Iterator<Seat> seatIterator = seats.iterator();
        while (seatIterator.hasNext()) {
            Seat seat = seatIterator.next();
            sb.append(seat);

            if (seatIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("; the price for this booking is " + seats.size() * 1500 + " HUF");
        return sb;
    }
}
