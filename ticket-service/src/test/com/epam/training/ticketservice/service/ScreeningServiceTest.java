package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        Screening screening = new Screening(movie, room, date);
        given(screeningRepository.findAll()).willReturn(List.of(screening));


        // When
        underTest.createScreening("Hello", "Room", date);

        // Then
        verify(movieRepository, times(1)).findByTitle("Hello");
        verify(roomRepository, times(1)).findByName("Room");
        verify(screeningRepository, times(1)).saveScreening(screening);
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