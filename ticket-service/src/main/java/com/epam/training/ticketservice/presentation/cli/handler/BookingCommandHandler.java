package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.service.BookingService;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup("Booking commands")
public class BookingCommandHandler extends SecuredCommandHandler {

    @Value("${ticket-service.base-ticket-price}")
    private int screeningPrice;

    BookingService bookingService;

    public BookingCommandHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ShellMethod(key = "book", value = "Book a ticket to a screening")
    @ShellMethodAvailability("isRegular")
    public String book(String movieTitle, String roomName, Date startTime, List<Seat> seats) {
        try {
            Booking booking = bookingService.saveBooking(movieTitle,
                roomName,
                startTime,
                seats);

            return mapBookingToString(seats);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private String mapBookingToString(List<Seat> seats) {
        StringBuilder sb = new StringBuilder("Seats booked: ");
        Iterator<Seat> seatIterator = seats.iterator();
        while (seatIterator.hasNext()) {
            Seat seat = seatIterator.next();
            sb.append(seat);

            if (seatIterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("; the price for this booking is ").append(seats.size() * screeningPrice).append(" HUF");
        return sb.toString();
    }
}
