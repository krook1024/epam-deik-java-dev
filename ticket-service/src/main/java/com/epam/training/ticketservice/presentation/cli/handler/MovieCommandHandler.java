package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@ShellCommandGroup("Movie commands")
public class MovieCommandHandler extends SecuredCommandHandler {

    MovieService movieService;

    public MovieCommandHandler(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethod(value = "Creates a movie", key = "create movie")
    @ShellMethodAvailability("isUserSignedIn")
    public String createMovie(String title, String genre, int length) {
        movieService.saveMovie(title, genre, length);
        return "Successfully created movie";
    }

    @ShellMethod(value = "Updates a movie", key = "update movie")
    @ShellMethodAvailability("isUserSignedIn")
    public String updateMovie(String title, String genre, int length) {
        Movie movie = new Movie(title, genre, length);
        movieService.updateMovie(title, movie);
        return "OK";
    }

    @ShellMethod(value = "Deletes a movie", key = "delete movie")
    @ShellMethodAvailability("isUserSignedIn")
    public void deleteMovie(String title) {
        movieService.deleteMovie(title);
    }

    @ShellMethod(value = "Lists movies", key = "list movies")
    public String listMovies() {
        List<Movie> movies = movieService.findAll();

        StringBuilder stringBuilder = new StringBuilder();

        movies.stream().forEach(
            movie -> {
                stringBuilder.append(movie.toString()).append("\n");
            }
        );

        return stringBuilder.toString();
    }
}
