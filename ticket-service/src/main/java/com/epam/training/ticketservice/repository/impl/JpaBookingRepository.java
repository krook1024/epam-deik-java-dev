package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.BookingDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.BookingProjection;
import com.epam.training.ticketservice.dataaccess.projection.SeatProjection;
import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.repository.BookingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaBookingRepository implements BookingRepository {

    BookingDao bookingDao;

    UserDao userDao;

    ScreeningDao screeningDao;

    public JpaBookingRepository(BookingDao bookingDao, UserDao userDao, ScreeningDao screeningDao) {
        this.bookingDao = bookingDao;
        this.userDao = userDao;
        this.screeningDao = screeningDao;
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingDao.save(mapToBookingProjection(booking));
    }

    @Override
    public List<Booking> findByUsername(String name) {
        return null;
    }

    private BookingProjection mapToBookingProjection(Booking booking) {
        BookingProjection bookingProjection = new BookingProjection();

        var seatProjections = mapSeatsToSeatProjections(booking.getSeats());
        bookingProjection.setSeats(seatProjections);

        bookingProjection.setUserProjection(
                userDao.findByName(booking.getUser().getName()).orElseThrow(
                        () -> new IllegalArgumentException("User cannot be found with the name" +
                                booking.getUser().getName())
                )
        );

        Screening screening = booking.getScreening();
        bookingProjection.setScreeningProjection(
                screeningDao.findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime(
                        screening.getMovie().getTitle(),
                        screening.getRoom().getName(),
                        screening.getStartTime()
                ).orElseThrow(() -> new IllegalArgumentException("Screening cannot be found"))
        );

        return bookingProjection;
    }

    private List<SeatProjection> mapSeatsToSeatProjections(List<Seat> seats) {
        return seats.stream().map(
                seat -> new SeatProjection(seat.getRow(), seat.getCol())
        ).collect(Collectors.toList());
    }
}
