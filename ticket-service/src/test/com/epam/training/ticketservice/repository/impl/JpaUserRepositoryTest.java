package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class JpaUserRepositoryTest {

    UserRepository underTest;

    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new JpaUserRepository(userDao);
    }

    @Test
    void testFindByNameThrowsIfUserIsNotFound() {
        // Given
        given(userDao.findByName("test")).willReturn(Optional.empty());

        // When, Then
        assertThrows(UsernameNotFoundException.class, () -> {
            underTest.findByName("test");
        });
    }
}