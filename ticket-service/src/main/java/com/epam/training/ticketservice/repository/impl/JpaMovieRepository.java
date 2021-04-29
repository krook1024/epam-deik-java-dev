package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaMovieRepository implements MovieRepository {

    MovieDao movieDao;

    public JpaMovieRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll()
                .stream()
                .map(
                        movieProjection -> new Movie(
                                movieProjection.getTitle(),
                                movieProjection.getGenre(),
                                movieProjection.getLength()
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public Movie findByTitle(String title) {
        MovieProjection movieProjection = movieDao.findByTitle(title).get();

        return new Movie(movieProjection.getTitle(),
                movieProjection.getGenre(),
                movieProjection.getLength());
    }

    @Override
    public void saveMovie(Movie movie) {
        movieDao.save(new MovieProjection(null, movie.getTitle(), movie.getGenre(), movie.getLength()));
    }

    @Override
    @Transactional
    public void update(String title, Movie movie) {
        MovieProjection movieProjection = movieDao.findByTitle(title).get();

        movieProjection.setGenre(movie.getGenre());
        movieProjection.setLength(movie.getLength());

        movieDao.save(movieProjection);
    }

    @Override
    @Transactional
    public void delete(String title) {
        movieDao.deleteByTitle(title);
    }

    private MovieProjection mapMovie(Movie movie) {
        return movieDao.findByTitle(movie.getTitle())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found for title " + movie.getTitle()));
    }
}
