package com.epam.training.ticketservice.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class SecuredCommandHandlerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testUserSignedOut() {
        // Given
        Authentication authentication = null;
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        SecuredCommandHandler underTest = new SecuredCommandHandler() {
        };

        // When, Then
        assertFalse(underTest.isRegular().isAvailable());
        assertFalse(underTest.isAdmin().isAvailable());
        assertFalse(underTest.isUserSignedIn().isAvailable());
        assertTrue(underTest.isUserSignedOut().isAvailable());
    }


    @Test
    void testUserSignedInAsAdmin() {
        // Given
        Authentication authentication = Mockito.mock(UsernamePasswordAuthenticationToken.class);

        given(authentication.isAuthenticated()).willReturn(true);
        willReturn(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
            .given(authentication).getAuthorities();

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        SecuredCommandHandler underTest = new SecuredCommandHandler() {
        };

        // When, Then
        assertFalse(underTest.isRegular().isAvailable());
        assertTrue(underTest.isAdmin().isAvailable());
        assertTrue(underTest.isUserSignedIn().isAvailable());
        assertFalse(underTest.isUserSignedOut().isAvailable());
    }


    void testUserSignedInAsRegular() {
        // Given
        Authentication authentication = Mockito.mock(UsernamePasswordAuthenticationToken.class);

        given(authentication.isAuthenticated()).willReturn(true);
        willReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")))
            .given(authentication).getAuthorities();

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        SecuredCommandHandler underTest = new SecuredCommandHandler() {
        };

        // When, Then
        assertTrue(underTest.isRegular().isAvailable());
        assertFalse(underTest.isAdmin().isAvailable());
        assertTrue(underTest.isUserSignedIn().isAvailable());
        assertFalse(underTest.isUserSignedOut().isAvailable());
    }
}