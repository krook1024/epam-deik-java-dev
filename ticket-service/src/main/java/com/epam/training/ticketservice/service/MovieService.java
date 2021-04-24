package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findByName(String name) {
        return movieRepository.findByTitle(name);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public void updateMovie(String title, Movie movie) {
        movieRepository.update(title, movie);
    }

    public void deleteMovie(String title) {
        Movie movie = movieRepository.findByTitle(title);
        movieRepository.delete(movie);
    }

    public void saveMovie(String title, String genre, int length) {
        movieRepository.saveMovie(new Movie(title, genre, length));
    }
}
