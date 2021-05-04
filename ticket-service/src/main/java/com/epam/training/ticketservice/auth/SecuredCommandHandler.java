package com.epam.training.ticketservice.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

public abstract class SecuredCommandHandler {
    public Availability isUserSignedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Availability.unavailable("you are not signed in. Please sign in to use this command");
        }

        return Availability.available();
    }

    public Availability isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Availability.unavailable("you are not signed in. Please sign in to use this command");
        }

        return checkAuthorities(authentication, false);
    }

    public Availability isRegular() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Availability.unavailable("you are not signed in. Please sign in to use this command");
        }

        return checkAuthorities(authentication, true);
    }

    private Availability checkAuthorities(Authentication authentication, Boolean acceptRegular) {
        if (acceptRegular) {
            if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return Availability.unavailable(
                        "you are not a regular user. Please sign in as a regular user to use this command"
                );
            }
        } else {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return Availability.unavailable(
                        "you are not an administrator. Please sign in as an administrator to usethis command"
                );
            }
        }

        return Availability.available();
    }
}
