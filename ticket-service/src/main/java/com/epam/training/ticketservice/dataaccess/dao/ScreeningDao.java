package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.EmbeddedScreeningId;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScreeningDao extends JpaRepository<ScreeningProjection, EmbeddedScreeningId> {
    List<ScreeningProjection> findAll();

    Optional<ScreeningProjection> findById_MovieProjection_TitleAndId_RoomProjection_NameAndId_StartTime(
            String movieTitle,
            String roomName,
            Date startTime
    );
}
