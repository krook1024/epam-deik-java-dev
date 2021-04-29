package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public ScreeningService(ScreeningRepository screeningRepository,
                            MovieRepository movieRepository,
                            RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public void createScreening(String movieTitle, String roomName, Date startTime) throws IllegalArgumentException {
        // todo check for overlaps
        // todo check for break period

        Movie movie = movieRepository.findByTitle(movieTitle);
        Room room = roomRepository.findByName(roomName);
        Screening screening = new Screening(movie, room, startTime);
        screeningRepository.saveScreening(screening);
    }

    public void deleteScreening(String movieTitle, String roomName, Date startTime) {
        Movie movie = movieRepository.findByTitle(movieTitle);
        Room room = roomRepository.findByName(roomName);
        Screening screening = new Screening(movie, room, startTime);
        screeningRepository.deleteScreening(screening);
    }
}
