package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class JpaMovieRepositoryTest {

    MovieRepository underTest;

    @Mock
    MovieDao movieDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new JpaMovieRepository(movieDao);
    }

    @Test
    void testFindAllMapsProjectionsToDomainObjects() {
        // Given
        given(movieDao.findAll()).willReturn(List.of(
                new MovieProjection(null, "Test", "Test", 90)
        ));

        // When
        List<Movie> movies = underTest.findAll();

        // Then
        assertEquals(movies, List.of(
                new Movie("Test", "Test", 90)
        ));
        verify(movieDao, times(1)).findAll();
    }

    @Test
    void testFindByTitleFindsTheRightObject() {
        // Given
        given(movieDao.findByTitle("Test")).willReturn(
                Optional.of(new MovieProjection(null, "Test", "Test", 90))
        );

        // When
        Movie movie = underTest.findByTitle("Test");

        // Then
        assertEquals(movie, new Movie("Test", "Test", 90));
        verify(movieDao).findByTitle("Test");
    }

    @Test
    void testSaveMovieCallsDaoWithTheRightParameters() {
        // When
        underTest.saveMovie(new Movie("Test", "Test", 90));

        // Then
        verify(movieDao).save(new MovieProjection(null, "Test", "Test", 90));
    }

    @Test
    void testUpdatesSetsParametersCorrectly() {
        // Given
        given(movieDao.findByTitle("Test")).willReturn(Optional.of(
                new MovieProjection(null, "Test", "Test", 90)
        ));

        // When
        underTest.update("Test", new Movie("Test", "Test2", 91));

        // Then
        verify(movieDao).save(new MovieProjection(null, "Test", "Test2", 91));
    }

    @Test
    void testDeleteCallsDao() {
        // When
        underTest.delete("Test");

        // Then
        verify(movieDao).deleteByTitle("Test");
    }
}