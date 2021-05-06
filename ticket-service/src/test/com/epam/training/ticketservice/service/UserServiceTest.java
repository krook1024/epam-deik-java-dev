package com.epam.training.ticketservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class UserServiceTest {

    UserService underTest;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository);
    }

    @Test
    void loadAdminUserByUsername() {
        // Given
        given(userRepository.findByName("admin")).willReturn(new User("admin", "admin", true));

        // When
        UserDetails userDetails = underTest.loadUserByUsername("admin");

        verify(userRepository, times(1)).findByName("admin");

        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertFalse(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(userDetails.getPassword(), "admin");
        assertEquals(userDetails.getUsername(), "admin");
    }

    @Test
    void loadBasicUserByUsername() {
        // Given
        given(userRepository.findByName("test")).willReturn(new User("test", "test", false));

        // When
        UserDetails userDetails = underTest.loadUserByUsername("test");

        verify(userRepository, times(1)).findByName("test");

        assertFalse(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(userDetails.getPassword(), "test");
        assertEquals(userDetails.getUsername(), "test");
    }

    @Test
    void testCreateUserCallsRepositoryCorrectly() {
        // When
        underTest.createUser("user", "user");

        // Then
        verify(userRepository).save(new User("user", "user", false));
    }
}