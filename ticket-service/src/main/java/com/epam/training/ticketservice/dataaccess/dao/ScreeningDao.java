package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.ScreeningProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningDao extends JpaRepository<ScreeningProjection, Long> {
    List<ScreeningProjection> findAll();
}
