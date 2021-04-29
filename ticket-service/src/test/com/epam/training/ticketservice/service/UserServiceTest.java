package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.projection.UserProjection;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void loadUserByUsername() {
        // Given
        given(userRepository.findByName("admin")).willReturn(new User("admin", "admin", true));

        // When
        UserDetails userDetails = underTest.loadUserByUsername("admin");

        verify(userRepository, times(1)).findByName("admin");

        System.out.println(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(userDetails.getPassword(), "admin");
        assertEquals(userDetails.getUsername(), "admin");
    }
}