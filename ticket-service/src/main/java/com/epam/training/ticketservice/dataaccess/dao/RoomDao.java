package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomDao extends JpaRepository<RoomProjection, Long> {
    Optional<RoomProjection> findByName(String name);

    List<RoomProjection> findAll();
}
