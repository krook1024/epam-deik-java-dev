package com.epam.training.ticketservice.dataaccess.dao;

import java.util.Optional;
import java.util.UUID;

import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserProjection, UUID> {
    Optional<UserProjection> findByName(String name);
}