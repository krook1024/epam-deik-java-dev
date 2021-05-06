package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import java.util.List;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup("Movie commands")
public class MovieCommandHandler extends SecuredCommandHandler {

    MovieService movieService;

    MovieRepository movieRepository;

    public MovieCommandHandler(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @ShellMethod(value = "Creates a movie", key = "create movie")
    @ShellMethodAvailability("isAdmin")
    public void createMovie(String title, String genre, int length) {
        movieService.saveMovie(title, genre, length);
    }

    @ShellMethod(value = "Updates a movie", key = "update movie")
    @ShellMethodAvailability("isAdmin")
    public void updateMovie(String title, String genre, int length) {
        Movie movie = new Movie(title, genre, length);
        movieRepository.update(title, movie);
    }

    @ShellMethod(value = "Deletes a movie", key = "delete movie")
    @ShellMethodAvailability("isAdmin")
    public void deleteMovie(String title) {
        movieRepository.delete(title);
    }

    @ShellMethod(value = "Lists movies", key = "list movies")
    public List<Movie> listMovies() {
        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            System.out.println("There are no movies at the moment");
        }

        return movies;
    }
}
