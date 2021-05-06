package com.epam.training.ticketservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.domain.Seat;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.BookingRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class BookingServiceTest {

    BookingService underTest;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    ScreeningRepository screeningRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        underTest = new BookingService(bookingRepository,
            screeningRepository,
            userRepository);

        Authentication authentication = Mockito.mock(UsernamePasswordAuthenticationToken.class);
        given(authentication.getName()).willReturn("Johnny Test");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testSaveBookingHappyPath() {
        // Given
        Date startTime = new Date();
        Screening foundScreening = new Screening(
            new Movie("Room", "Genre", 420),
            new Room("Room", 42, 42),
            startTime
        );
        given(screeningRepository.findByMovieTitleAndRoomNameAndStartTime("Movie",
            "Room",
            startTime))
            .willReturn(foundScreening);
        User foundUser = new User(
            "Johnny Test",
            "secret",
            false
        );
        given(userRepository.findByName("Johnny Test")).willReturn(foundUser);
        List<Seat> seats = List.of(new Seat(1, 1));
        List<Seat> invalidSeats = List.of(new Seat(69, 420));

        // When
        underTest.saveBooking("Movie", "Room", startTime, seats);

        // Then
        verify(bookingRepository).saveBooking(
            new Booking(
                foundScreening,
                foundUser,
                seats
            )
        );
    }

    @Test
    void testSaveBookingSeatIsAlreadyTakenOrInvalid() {
        // Given
        Date startTime = new Date();
        Screening foundScreening = new Screening(
            new Movie("Room", "Genre", 420),
            new Room("Room", 42, 42),
            startTime
        );
        User foundUser = new User(
            "Johnny Test",
            "secret",
            false
        );
        List<Seat> seats = List.of(new Seat(1, 1));
        given(bookingRepository.findAllByScreening(foundScreening)).willReturn(List.of(
            new Booking(foundScreening, foundUser, seats)
        ));
        given(screeningRepository.findByMovieTitleAndRoomNameAndStartTime("Movie",
            "Room",
            startTime))
            .willReturn(foundScreening);
        given(userRepository.findByName("Johnny Test")).willReturn(foundUser);

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveBooking(
                "Movie",
                "Room",
                startTime,
                seats
            );
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveBooking(
                "Movie",
                "Room",
                startTime,
                seats
            );
        });
    }
}