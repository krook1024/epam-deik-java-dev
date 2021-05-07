package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRoomRepository implements RoomRepository {

    RoomDao roomDao;

    public JpaRoomRepository(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public List<Room> findAll() {
        return roomDao.findAll()
            .stream()
            .map(
                roomProjection -> new Room(
                    roomProjection.getName(),
                    roomProjection.getRows(),
                    roomProjection.getCols()
                )
            )
            .collect(Collectors.toList());
    }

    @Override
    public Room findByName(String name) {
        RoomProjection roomProjection = roomDao.findByName(name).orElseThrow(
            () -> new IllegalArgumentException("No room can be found with this name")
        );

        return new Room(
            roomProjection.getName(),
            roomProjection.getRows(),
            roomProjection.getCols()
        );
    }

    @Override
    public void saveRoom(Room room) {
        roomDao.save(new RoomProjection(room.getName(), room.getRows(), room.getCols()));
    }

    @Override
    @Transactional
    public void update(String name, Room room) {
        RoomProjection roomProjection = roomDao.findByName(name).orElseThrow(
            () -> new IllegalArgumentException("No room can be found with this name")
        );

        roomProjection.setRows(room.getRows());
        roomProjection.setCols(room.getCols());

        roomDao.save(roomProjection);
    }

    @Override
    @Transactional
    public void delete(String name) {
        roomDao.deleteByName(name);
    }
}
