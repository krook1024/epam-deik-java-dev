package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ScreeningService {

    @Value("${ticket-service.base-ticket-price}")
    private static int breakPeriodLength;
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

    public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return !start1.after(end2) && !start2.after(end1);
    }

    public void createScreening(String movieTitle, String roomName, Date startTime) throws IllegalArgumentException {
        // todo check for overlaps
        // todo check for break period

        Movie movie = movieRepository.findByTitle(movieTitle);
        Room room = roomRepository.findByName(roomName);

        checkScreeningOverlap(roomName, startTime, movie.getLength());

        Screening screening = new Screening(movie, room, startTime);
        screeningRepository.saveScreening(screening);
    }

    public void deleteScreening(String movieTitle, String roomName, Date startTime) {
        Movie movie = movieRepository.findByTitle(movieTitle);
        Room room = roomRepository.findByName(roomName);
        Screening screening = new Screening(movie, room, startTime);
        screeningRepository.deleteScreening(screening);
    }

    private void checkScreeningOverlap(String roomName,
        Date desiredStartTime,
        int length) throws IllegalArgumentException {
        List<Screening> screenings = screeningRepository.findAll();

        screenings.forEach(screening -> {
            if (screening.getRoom().getName().equals(roomName)) {
                Date screeningStartTime = screening.getStartTime();
                Date screeningEnd = DateUtils.addMinutes(screeningStartTime, screening.getMovie().getLength());
                Date screeningBreakPeriodEnd = DateUtils.addMinutes(screeningEnd, breakPeriodLength);

                Date desiredEndTime = DateUtils.addMinutes(desiredStartTime, length);

                if (isOverlapping(screeningStartTime, screeningEnd, desiredStartTime, desiredEndTime)) {
                    throw new IllegalArgumentException("There is an overlapping screening");
                }

                if (isOverlapping(screeningStartTime, screeningBreakPeriodEnd, desiredStartTime, desiredEndTime)) {
                    throw new IllegalArgumentException(
                        "This would start in the break period after another screening in this room"
                    );
                }
            }
        });
    }
}
