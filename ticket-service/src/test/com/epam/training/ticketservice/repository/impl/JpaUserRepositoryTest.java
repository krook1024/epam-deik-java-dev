package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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
        assertThrows(UsernameNotFoundException.class, () -> underTest.findByName("test"));
    }

    @Test
    void testFindByNameReturnsCorrectUserIfUserIsFound() {
        // Given
        UserProjection userProjection = new UserProjection(null, "test", "test", false);
        given(userDao.findByName("test")).willReturn(Optional.of(userProjection));

        // When
        User found = underTest.findByName("test");

        // Then
        assertEquals(userProjection.getName(), found.getName());
        assertEquals(userProjection.getPassword(), found.getPassword());
        assertEquals(userProjection.getIsAdmin(), found.getIsAdmin());
    }

    @Test
    void testSaveCallsDaoWithTheRightParameters() {
        // When
        underTest.save(new User("user", "user", false));

        // Then
        verify(userDao).save(new UserProjection(null, "user", "user", false));
    }
}