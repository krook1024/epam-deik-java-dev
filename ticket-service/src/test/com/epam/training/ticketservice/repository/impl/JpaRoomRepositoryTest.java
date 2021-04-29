package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class JpaRoomRepositoryTest {

    RoomRepository underTest;

    @Mock
    RoomDao roomDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new JpaRoomRepository(roomDao);
    }

    @Test
    void testFindAllReturnsItemCorrectly() {
        // Given
        given(roomDao.findAll()).willReturn(
                List.of(
                        new RoomProjection(null, "Room#1", 6, 6),
                        new RoomProjection(null, "Room#2", 3, 4)
                )
        );

        // When
        List<Room> rooms = underTest.findAll();

        // Then
        assertTrue(rooms.contains(new Room("Room#1", 6, 6)));
        assertTrue(rooms.contains(new Room("Room#2", 3, 4)));
        assertEquals(2, rooms.stream().count());
        verify(roomDao, times(1)).findAll();
    }

    @Test
    void testFindAllWhenResultIsEmpty() {
        // Given
        given(roomDao.findAll()).willReturn(List.of());

        // When
        List<Room> rooms = underTest.findAll();

        // Then
        assertEquals(0, rooms.stream().count());
    }

    @Test
    void testFindByNameReturnsRoomWithTheCorrectName() {
        // Given
        given(roomDao.findByName("Test")).willReturn(Optional.of(new RoomProjection(null, "Test", 4, 4)));

        // When
        Room room = underTest.findByName("Test");

        // Then
        assertEquals("Test", room.getName());
        verify(roomDao, times(1)).findByName("Test");
    }

    @Test
    void testSaveRoomCallsDaoCorrectly() {
        // Given
        Room room = new Room("Test", 4, 4);

        // When
        underTest.saveRoom(room);

        // Then
        verify(roomDao).save(new RoomProjection(null, "Test", 4, 4));
    }

    @Test
    void testUpdateRoomCallsDaoCorrectly() {
        // Given
        Room updateWith = new Room("Test", 4, 2);
        RoomProjection foundRoomProjection = new RoomProjection("Test", 4, 4);
        given(roomDao.findByName("Test")).willReturn(Optional.of(foundRoomProjection));

        // When
        underTest.update("Test", updateWith);

        // Then
        verify(roomDao).save(new RoomProjection("Test", 4, 2));
    }

    @Test
    void testDeleteRoomCallsDaoCorrectly() {
        // When
        underTest.delete("Test");

        // Then
        verify(roomDao).deleteByName("Test");
    }
}