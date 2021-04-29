package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.presentation.cli.io.InputReader;
import com.epam.training.ticketservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthCommandHandlerTest {

    AuthCommandHandler underTest;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserService userService;

    @Mock
    InputReader inputReader;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AuthCommandHandler(
                userService,
                inputReader,
                authenticationManager
        );
    }
}