package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByName(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }
}
