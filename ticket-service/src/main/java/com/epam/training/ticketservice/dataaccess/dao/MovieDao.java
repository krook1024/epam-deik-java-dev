package com.epam.training.ticketservice.dataaccess.dao;

import com.epam.training.ticketservice.dataaccess.projection.MovieProjection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDao extends JpaRepository<MovieProjection, Long> {

    Optional<MovieProjection> findByTitle(String title);

    void deleteByTitle(String title);
}
