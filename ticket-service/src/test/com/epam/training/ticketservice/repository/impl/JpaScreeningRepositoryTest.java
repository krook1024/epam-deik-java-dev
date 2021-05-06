package com.epam.training.ticketservice.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JpaScreeningRepositoryTest {

    ScreeningRepository underTest;

    @Mock
    ScreeningDao screeningDao;

    @Mock
    MovieDao movieDao;

    @Mock
    RoomDao roomDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new JpaScreeningRepository(screeningDao, movieDao, roomDao);
    }

    @Test
    void testSaveScreeningCallsDaoCorrectlyWhenEverythingIsAsIntended() {
        // Given
        Date date = new Date();
        given(movieDao.findByTitle("TestMovie")).willReturn(
            Optional.of(new MovieProjection(null, "TestMovie", "Test", 90))
        );
        given(roomDao.findByName("TestRoom")).willReturn(
            Optional.of(new RoomProjection(null, "TestRoom", 4, 4))
        );

        // When
        underTest.saveScreening(new Screening(
            new Movie("TestMovie", "Test", 90),
            new Room("TestRoom", 4, 4),
            date
        ));

        verify(movieDao).findByTitle("TestMovie");
        verify(roomDao).findByName("TestRoom");
        verify(screeningDao).save(new ScreeningProjection(
            new EmbeddedScreeningId(
                new MovieProjection(null, "TestMovie", "Test", 90),
                new RoomProjection(null, "TestRoom", 4, 4),
                date
            )
        ));
    }

    @Test
    void testSaveScreeningWhenMovieIsNotFound() {
        // Given
        Date date = new Date();
        given(movieDao.findByTitle("TestMovie")).willReturn(
            Optional.empty()
        );
        given(roomDao.findByName("TestRoom")).willReturn(
            Optional.of(new RoomProjection(null, "TestRoom", 4, 4))
        );

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> underTest.saveScreening(
            new Screening(
                new Movie("TestMovie", "Test", 90),
                new Room("TestRoom", 4, 4),
                date
            )
            )
        );
    }

    @Test
    void testSaveScreeningWhenRoomIsNotFound() {
        // Given
        Date date = new Date();
        given(movieDao.findByTitle("TestMovie")).willReturn(
            Optional.of(new MovieProjection(null, "TestMovie", "Test", 90))
        );
        given(roomDao.findByName("TestRoom")).willReturn(
            Optional.empty()
        );

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> underTest.saveScreening(
            new Screening(
                new Movie("TestMovie", "Test", 90),
                new Room("TestRoom", 4, 4),
                date
            )
            )
        );
    }

    @Test
    void testDeleteScreeningCallsDaoCorrectly() {
        // Given
        Date date = new Date();
        given(movieDao.findByTitle("TestMovie")).willReturn(
            Optional.of(new MovieProjection(null, "TestMovie", "Test", 90))
        );
        given(roomDao.findByName("TestRoom")).willReturn(
            Optional.of(new RoomProjection(null, "TestRoom", 4, 4))
        );

        // When
        underTest.deleteScreening(
            new Screening(
                new Movie("TestMovie", "Test", 90),
                new Room("TestRoom", 4, 4),
                date
            )
        );

        // Then
        verify(screeningDao).delete(new ScreeningProjection(
            new EmbeddedScreeningId(
                new MovieProjection(null, "TestMovie", "Test", 90),
                new RoomProjection(null, "TestRoom", 4, 4),
                date
            )
        ));
    }

    @Test
    void testFindAllMapsItemsCorrectly() {
        // Given
        Date date = new Date();
        given(screeningDao.findAll()).willReturn(List.of(
            new ScreeningProjection(new EmbeddedScreeningId(
                new MovieProjection(null, "TestMovie", "Test", 90),
                new RoomProjection(null, "TestRoom", 4, 4),
                date
            )
            )
        ));

        // When
        List<Screening> screenings = underTest.findAll();

        // Then
        assertEquals(screenings, List.of(
            new Screening(
                new Movie("TestMovie", "Test", 90),
                new Room("TestRoom", 4, 4),
                date
            )
        ));
    }

    @Test
    void testFindByMovieTitleAndRoomNameAndStartTimeShouldNotThrowException() {
        // Given
        Date date = new Date();
        Screening expected = new Screening(
            new Movie("Movie", "Genre", 420),
            new Room("Room", 4, 2),
            date
        );
        ScreeningProjection expectedProjection = new ScreeningProjection(
            new EmbeddedScreeningId(
                new MovieProjection(null, "Movie", "Genre", 420),
                new RoomProjection(null, "Room", 4, 2),
                date
            )
        );
        given(
            screeningDao.findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime("Movie",
                "Room",
                date)
        ).willReturn(Optional.of(expectedProjection));

        // When
        Screening result = underTest.findByMovieTitleAndRoomNameAndStartTime("Movie", "Room", date);

        // Then
        assertEquals(expected, result);
        verify(screeningDao).findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime("Movie",
            "Room",
            date);

    }


    @Test
    void testFindByMovieTitleAndRoomNameAndStartTimeShouldThrowException() {
        // Given
        Date date = new Date();
        given(
            screeningDao.findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime("Movie",
                "Room",
                date)
        ).willReturn(Optional.empty());

        // When, Then
        assertThrows(IllegalArgumentException.class,
            () -> underTest.findByMovieTitleAndRoomNameAndStartTime("Movie",
                "Room",
                date));
    }
}