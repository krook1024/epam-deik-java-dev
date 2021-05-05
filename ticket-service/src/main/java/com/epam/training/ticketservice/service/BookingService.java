package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.BookingRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private ScreeningRepository screeningRepository;
    private UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          ScreeningRepository screeningRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.screeningRepository = screeningRepository;
        this.userRepository = userRepository;
    }

    public Booking saveBooking(String movieTitle, String roomName, Date startTime, List<Seat> seats) {
        Screening screening = screeningRepository.findByMovieTitleAndRoomNameAndStartTime(
                movieTitle,
                roomName,
                startTime
        );

        User user = userRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName());

        Booking booking = new Booking(
                screening,
                user,
                seats
        );

        checkSeatOverflow(booking);
        checkIfAnySeatIsAlreadyBooked(booking);

        bookingRepository.saveBooking(booking);

        return booking;
    }

    private void checkSeatOverflow(Booking booking) {
        int maximumRowNumber = booking.getScreening().getRoom().getRows();
        int maximumColNumber = booking.getScreening().getRoom().getCols();

        for (Seat seat : booking.getSeats()) {
            if (maximumRowNumber < seat.getRow() || maximumColNumber < seat.getCol()) {
                throw new IllegalArgumentException("Seat " + seat.getRow() + "," + seat.getCol() + " does not exist " +
                        "in this room");
            }
        }
    }

    private void checkIfAnySeatIsAlreadyBooked(Booking booking) {
        var seats = bookingRepository
                .findAllByScreening(booking.getScreening())
                .stream()
                .map(Booking::getSeats)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Seat seat : seats) {
            if (booking.getSeats().contains(seat)) {
                throw new IllegalArgumentException("Seat " + seat.getRow() + "," + seat.getCol() + " is already taken");
            }
        }
    }
}
