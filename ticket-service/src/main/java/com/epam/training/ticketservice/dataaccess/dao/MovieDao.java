package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieDao extends JpaRepository<MovieProjection, Long> {
    Optional<MovieProjection> findByTitle(String title);

    void deleteByTitle(String title);

    List<MovieProjection> findAll();
}
