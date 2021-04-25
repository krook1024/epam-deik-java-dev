package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Movie;

import java.util.List;

public interface MovieRepository {

    void saveMovie(Movie movie);

    Movie findByTitle(String title);

    void update(String title, Movie movie);

    List<Movie> findAll();

    void delete(String title);
}
