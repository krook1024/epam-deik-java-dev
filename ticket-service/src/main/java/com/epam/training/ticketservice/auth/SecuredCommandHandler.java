package com.epam.training.ticketservice.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    public Availability isUserSignedOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return Availability.available();
        }

        return Availability.unavailable("you are signed in. Please sign out to use this command");
    }
}
