package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.MovieDao;
import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.projection.EmbeddedScreeningId;
import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaScreeningRepository implements ScreeningRepository {

    ScreeningDao screeningDao;

    MovieDao movieDao;

    RoomDao roomDao;


    public JpaScreeningRepository(ScreeningDao screeningDao,
                                  MovieDao movieDao,
                                  RoomDao roomDao) {
        this.screeningDao = screeningDao;
        this.movieDao = movieDao;
        this.roomDao = roomDao;
    }

    @Override
    @Transactional
    public void saveScreening(Screening screening) {
        ScreeningProjection screeningProjection = mapToScreeningProjection(screening);
        screeningDao.save(screeningProjection);
    }

    @Override
    @Transactional
    public void deleteScreening(Screening screening) {
        ScreeningProjection screeningProjection = mapToScreeningProjection(screening);
        screeningDao.delete(screeningProjection);
    }

    @Override
    public List<Screening> findAll() {
        return mapToScreenings(screeningDao.findAll());
    }

    protected List<Screening> mapToScreenings(List<ScreeningProjection> screeningProjections) {
        return screeningProjections.stream()
                .map(this::mapToScreening)
                .collect(Collectors.toList());
    }

    protected Screening mapToScreening(ScreeningProjection screeningProjection) {
        MovieProjection movieProjection = screeningProjection.getId().getMovieProjection();
        RoomProjection roomProjection = screeningProjection.getId().getRoomProjection();
        Date startTime = screeningProjection.getId().getStartTime();

        Movie movie = new Movie(movieProjection.getTitle(),
                movieProjection.getGenre(),
                movieProjection.getLength());
        Room room = new Room(roomProjection.getName(),
                roomProjection.getRows(),
                roomProjection.getCols());

        return new Screening(movie, room, startTime);
    }

    protected ScreeningProjection mapToScreeningProjection(Screening screening) throws IllegalArgumentException {
        Optional<MovieProjection> movieProjectionOptional = movieDao.findByTitle(screening.getMovie().getTitle());
        Optional<RoomProjection> roomProjectionOptional = roomDao.findByName(screening.getRoom().getName());
        MovieProjection movieProjection = null;
        RoomProjection roomProjection = null;

        if (movieProjectionOptional.isEmpty()) {
            throw new IllegalArgumentException("No movie can be found for name " + screening.getMovie().getTitle());
        } else {
            movieProjection = movieProjectionOptional.get();
        }

        if (roomProjectionOptional.isEmpty()) {
            throw new IllegalArgumentException("No room can be found for name " + screening.getMovie().getTitle());
        } else {
            roomProjection = roomProjectionOptional.get();
        }

        EmbeddedScreeningId embeddedScreeningId = new EmbeddedScreeningId(movieProjection,
                roomProjection,
                screening.getStartTime());

        return new ScreeningProjection(embeddedScreeningId);
    }
}
