package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Room;
import java.util.List;

public interface RoomRepository {

    List<Room> findAll();

    Room findByName(String name);

    void saveRoom(Room room);

    void update(String name, Room room);

    void delete(String name);
}
