package com.epam.training.ticketservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.presentation.cli.DateConverterComponent;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScreeningServiceTest {

    private ScreeningService underTest;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ScreeningService(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    void createScreeningShouldNotThrowException() {
        // Given
        Date date = new Date();

        Movie movie = new Movie("Hello", "Test", 119);
        given(movieRepository.findByTitle("Hello")).willReturn(movie);

        Room room = new Room("Room", 3, 4);
        given(roomRepository.findByName("Room")).willReturn(room);

        Room otherRoom = new Room("OtherRoom", 6, 9);

        Screening screening = new Screening(movie, room, date);
        given(screeningRepository.findAll()).willReturn(List.of(
            new Screening(
                movie,
                room,
                new DateConverterComponent().convert("2020-01-01 10:00:00")
            ),
            new Screening(
                movie,
                otherRoom,
                new DateConverterComponent().convert("2029-01-01 10:00:00")
            ),
            new Screening(
                movie,
                room,
                new DateConverterComponent().convert("1990-01-01 10:00:00")
            )));

        // When
        underTest.createScreening("Hello", "Room", date);

        // Then
        verify(movieRepository).findByTitle("Hello");
        verify(roomRepository).findByName("Room");
        verify(screeningRepository).saveScreening(screening);
    }

    @Test
    void createScreeningShouldThrowIllegalArgumentException() {
        // Given
        Date date = new DateConverterComponent().convert("2020-01-01 10:00:00");
        Date postponedDate = DateUtils.addMinutes(date, 121);
        Date beforeDate = DateUtils.addMinutes(date, -110);

        Movie movie = new Movie("Hello", "Test", 119);
        given(movieRepository.findByTitle("Hello")).willReturn(movie);

        Room room = new Room("Room", 3, 4);
        given(roomRepository.findByName("Room")).willReturn(room);

        Screening screening = new Screening(movie, room, date);
        given(screeningRepository.findAll()).willReturn(List.of(
            new Screening(
                movie,
                room,
                date
            ),
            new Screening(
                movie,
                room,
                DateUtils.addMinutes(date, -121)
            )));

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.createScreening("Hello", "Room", date);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            underTest.createScreening("Hello", "Room", postponedDate);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            underTest.createScreening("Hello", "Room", beforeDate);
        });
    }

    @Test
    void deleteScreeningShouldNotThrowException() {
        // Given
        Date date = new Date();

        Movie movie = new Movie("Hello", "Test", 119);
        given(movieRepository.findByTitle("Hello")).willReturn(movie);

        Room room = new Room("Room", 3, 4);
        given(roomRepository.findByName("Room")).willReturn(room);

        Screening screening = new Screening(movie, room, date);
        given(screeningRepository.findAll()).willReturn(List.of(screening));

        // When
        underTest.deleteScreening("Hello", "Room", date);

        // Then
        verify(movieRepository, times(1)).findByTitle("Hello");
        verify(roomRepository, times(1)).findByName("Room");
        verify(screeningRepository, times(1)).deleteScreening(screening);
    }
}