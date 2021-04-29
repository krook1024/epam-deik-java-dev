package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}