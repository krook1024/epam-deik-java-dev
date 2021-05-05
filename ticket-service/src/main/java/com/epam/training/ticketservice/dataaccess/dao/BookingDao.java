package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.BookingProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingDao extends JpaRepository<BookingProjection, Long> {
    List<BookingProjection> findAllByUserProjection_Name(String userProjection_Name);

    List<BookingProjection> findAllByScreeningProjection(ScreeningProjection screeningProjection);
}
