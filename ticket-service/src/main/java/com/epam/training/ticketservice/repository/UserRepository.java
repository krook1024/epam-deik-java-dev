package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.User;

public interface UserRepository {
    User findByName(String name);

    void save(User user);
}
