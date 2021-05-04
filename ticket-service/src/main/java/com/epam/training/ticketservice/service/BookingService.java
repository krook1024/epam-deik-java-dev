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

import java.util.Date;
import java.util.List;

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

    public void saveBooking(String movieTitle, String roomName, Date startTime, List<Seat> seats) {
        // todo check seat overlap
        // todo check if seat exists in room

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

        bookingRepository.saveBooking(booking);
    }
}
