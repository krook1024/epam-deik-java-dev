package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.BookingDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.BookingProjection;
import com.epam.training.ticketservice.dataaccess.projection.EmbeddedScreeningId;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.SeatProjection;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.BookingRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

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
    public List<Booking> findAllByScreening(Screening screening) {
        return mapToBookings(bookingDao.findAllByScreeningProjection(
            screeningDao.findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime(
                screening.getMovie().getTitle(),
                screening.getRoom().getName(),
                screening.getStartTime()
            ).orElseThrow(() -> new IllegalArgumentException("Screening cannot be found"))
        ));
    }

    private List<Booking> mapToBookings(List<BookingProjection> bookingProjections) {
        return bookingProjections.stream().map(this::mapToBooking).collect(Collectors.toList());
    }

    private Booking mapToBooking(BookingProjection bookingProjection) {
        Booking.BookingBuilder builder = Booking.builder();

        EmbeddedScreeningId embeddedScreeningId = bookingProjection.getScreeningProjection().getId();
        MovieProjection movieProjection = embeddedScreeningId.getMovieProjection();
        RoomProjection roomProjection = embeddedScreeningId.getRoomProjection();

        builder.screening(
            new Screening(
                new Movie(
                    movieProjection.getTitle(),
                    movieProjection.getGenre(),
                    movieProjection.getLength()
                ),
                new Room(
                    roomProjection.getName(),
                    roomProjection.getRows(),
                    roomProjection.getCols()
                ),
                embeddedScreeningId.getStartTime()
            )
        );

        builder.seats(
            bookingProjection.getSeats().stream().map(
                seatProjection -> new Seat(seatProjection.getRowNum(), seatProjection.getColNum())
            ).collect(Collectors.toList())
        );

        UserProjection userProjection = bookingProjection.getUserProjection();

        builder.user(
            new User(
                userProjection.getName(),
                userProjection.getPassword(),
                userProjection.getIsAdmin()
            )
        );

        return builder.build();
    }

    private BookingProjection mapToBookingProjection(Booking booking) {
        BookingProjection bookingProjection = new BookingProjection();

        var seatProjections = mapSeatsToSeatProjections(booking.getSeats());
        bookingProjection.setSeats(seatProjections);

        bookingProjection.setUserProjection(
            userDao.findByName(booking.getUser().getName()).orElseThrow(
                () -> new IllegalArgumentException(
                    "User cannot be found with the name" + booking.getUser().getName()
                )
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

    @Override
    public List<Booking> findByUserName(String userName) {
        var bookingProjections = bookingDao.findAllByUserProjection_Name(userName);
        return mapToBookings(bookingProjections);
    }

    private List<SeatProjection> mapSeatsToSeatProjections(List<Seat> seats) {
        return seats.stream().map(
            seat -> new SeatProjection(seat.getRow(), seat.getCol())
        ).collect(Collectors.toList());
    }
}
