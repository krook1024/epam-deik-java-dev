package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.BookingProjection;
import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDao extends JpaRepository<BookingProjection, Long> {

    List<BookingProjection> findAllByUserProjection_Name(String userProjectionName);

    List<BookingProjection> findAllByScreeningProjection(ScreeningProjection screeningProjection);
}
