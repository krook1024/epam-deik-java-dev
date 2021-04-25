package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.User;

public interface UserRepository {
    void saveUser(User user);

    User findByName(String name);
}
