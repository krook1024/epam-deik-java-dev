package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<RoomProjection, Long> {

    Optional<RoomProjection> findByName(String name);

    void deleteByName(String name);
}
