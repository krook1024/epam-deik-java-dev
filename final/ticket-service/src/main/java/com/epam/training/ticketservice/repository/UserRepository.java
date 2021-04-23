package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.User;
import org.springframework.stereotype.Repository;

public interface UserRepository {
    void saveUser(User user);
}
