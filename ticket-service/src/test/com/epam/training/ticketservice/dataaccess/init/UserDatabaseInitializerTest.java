package com.epam.training.ticketservice.dataaccess.init;

import com.epam.training.ticketservice.dataaccess.dao.UserDao;
import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserDatabaseInitializerTest {

    UserDatabaseInitializer underTest;

    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserDatabaseInitializer(userDao);
    }

    @Test
    void initDatabaseWhenAdminIsPresent() {
        // Given
        UserProjection userProjection = new UserProjection(null, "hello", "hello", true);
        given(userDao.findByName("admin")).willReturn(Optional.of(userProjection));

        // When
        underTest.initDatabase();

        // Then
        verify(userDao, times(0)).save(userProjection);
    }

    @Test
    void initDatabaseWhenAdminIsNotPresent() {
        // Given
        UserProjection userProjection = new UserProjection(null, "admin", "admin", true);
        given(userDao.findByName("admin")).willReturn(Optional.empty());

        // When
        underTest.initDatabase();

        // Then
        verify(userDao, times(1)).save(userProjection);
    }
}