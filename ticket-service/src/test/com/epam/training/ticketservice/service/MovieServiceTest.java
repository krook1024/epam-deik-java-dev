package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MovieServiceTest {

    MovieService underTest;

    @Mock
    MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new MovieService(movieRepository);
    }

    @Test
    void testSaveMovie() {
        // Given
        Movie movie = new Movie("test", "test", 90);

        // When
        underTest.saveMovie("test", "test", 90);

        // Then
        verify(movieRepository, times(1)).saveMovie(movie);
    }
}