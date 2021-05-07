package com.epam.training.ticketservice.repository.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.epam.training.ticketservice.dataaccess.dao.BookingDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.BookingProjection;
import com.epam.training.ticketservice.dataaccess.projection.EmbeddedScreeningId;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import com.epam.training.ticketservice.dataaccess.projection.SeatProjection;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.BookingRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JpaBookingRepositoryTest {
    BookingRepository underTest;

    @Mock
    BookingDao bookingDao;

    @Mock
    UserDao userDao;

    @Mock
    ScreeningDao screeningDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new JpaBookingRepository(bookingDao, userDao, screeningDao);
    }

    @Test
    void testSaveBookingCallsDaoCorrectly() {
        // Given
        Movie movie = new Movie("Title", "Genre", 420);
        Room room = new Room("Room", 6, 6);
        Date startTime = new Date();
        Screening screening = new Screening(movie, room, startTime);
        List<Seat> seats = List.of(new Seat(1, 1), new Seat(1, 2));
        User user = new User("Johnny Test", "secret", false);
        Booking booking = new Booking(
            screening,
            user,
            seats
        );

        MovieProjection movieProjection = new MovieProjection(null, "Title", "Genre", 420);
        RoomProjection roomProjection = new RoomProjection(null, "Room", 6, 6);
        UserProjection userProjection = new UserProjection(null,
            "Johnny Test",
            "secret",
            false);
        ScreeningProjection screeningProjection = new ScreeningProjection(
            new EmbeddedScreeningId(movieProjection, roomProjection, startTime));
        List<SeatProjection> seatProjections = List.of(new SeatProjection(1, 1), new SeatProjection(1,
            2));
        BookingProjection bookingProjection = new BookingProjection(null,
        screeningProjection,
        userProjection,
        seatProjections);
        given(userDao.findByName("Johnny Test")).willReturn(Optional.of(userProjection));
        given(screeningDao.findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime(
            "Title", "Room", startTime
        )).willReturn(Optional.of(screeningProjection));

        // When
        underTest.saveBooking(booking);

        // Then
        verify(bookingDao).save(bookingProjection);
    }

    @Test
    void testFindAllByScreening() {
        // Given
        Movie movie = new Movie("Title", "Genre", 420);
        Room room = new Room("Room", 6, 6);
        Date startTime = new Date();
        Screening screening = new Screening(movie, room, startTime);
        List<Seat> seats = List.of(new Seat(1, 1), new Seat(1, 2));
        User user = new User("Johnny Test", "secret", false);
        Booking booking = new Booking(
            screening,
            user,
            seats
        );

        MovieProjection movieProjection = new MovieProjection(null, "Title", "Genre", 420);
        RoomProjection roomProjection = new RoomProjection(null, "Room", 6, 6);
        UserProjection userProjection = new UserProjection(null,
            "Johnny Test",
            "secret",
            false);
        ScreeningProjection screeningProjection = new ScreeningProjection(
            new EmbeddedScreeningId(movieProjection, roomProjection, startTime));
        List<SeatProjection> seatProjections = List.of(new SeatProjection(1, 1), new SeatProjection(1,
            2));
        BookingProjection bookingProjection = new BookingProjection(null,
            screeningProjection,
            userProjection,
            seatProjections);
        given(screeningDao.findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime(
            "Title", "Room", startTime
        )).willReturn(Optional.of(screeningProjection));
        given(bookingDao.findAllByScreeningProjection(screeningProjection))
            .willReturn(List.of(bookingProjection));

        // When
        List<Booking> result = underTest.findAllByScreening(screening);

        // Then
        assertEquals(List.of(booking), result);
    }

    @Test
    void testFindByUserName() {
        // When
        underTest.findByUserName("Johnny Test");

        // Then
        verify(bookingDao).findAllByUserProjection_Name("Johnny Test");
        verifyNoMoreInteractions(bookingDao);
    }
}