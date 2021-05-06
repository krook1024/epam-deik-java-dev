package com.epam.training.ticketservice.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RoomServiceTest {

    RoomService underTest;

    @Mock
    RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RoomService(roomRepository);
    }

    @Test
    void testSaveRoomCallsRepositoryCorrectly() {
        // Given
        Room room = new Room("test", 42, 42);

        // When
        underTest.saveRoom("test", 42, 42);

        // Then
        verify(roomRepository, times(1)).saveRoom(room);
    }

    @Test
    void testUpdateRoomCallsRepositoryCorrectly() {
        // Given
        Room updatedRoom = new Room("test", 42, 44);

        // When
        underTest.updateRoom("test", updatedRoom);

        // Then
        verify(roomRepository).update("test", updatedRoom);
    }
}