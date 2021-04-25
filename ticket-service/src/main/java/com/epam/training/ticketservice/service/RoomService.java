package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public void updateRoom(String name, Room room) {
        roomRepository.update(name, room);
    }

    public void deleteRoom(String name) {
        Room room = roomRepository.findByName(name);
        roomRepository.delete(room);
    }

    public void saveRoom(String name, int rows, int cols) {
        roomRepository.saveRoom(new Room(name, rows, cols));
    }
}
