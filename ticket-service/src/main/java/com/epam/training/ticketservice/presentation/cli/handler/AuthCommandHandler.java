package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.presentation.cli.io.InputReader;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class AuthCommandHandler extends SecuredCommandHandler {
    private UserService userService;

    InputReader inputReader;

    AuthenticationManager authenticationManager;

    public AuthCommandHandler(UserService userService,
                              InputReader inputReader,
                              AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.inputReader = inputReader;
        this.authenticationManager = authenticationManager;
    }

    @ShellMethod(value = "Attempts a login as admin", key = "sign in privileged")
    @ShellMethodAvailability("isUserSignedOut")
    public String loginPrivileged(String name, String password) {
        Authentication req = new UsernamePasswordAuthenticationToken(name, password);

        try {
            Authentication result = authenticationManager.authenticate(req);
            SecurityContextHolder.getContext().setAuthentication(result);
            return null;
        } catch (AuthenticationException e) {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "Logs out", key = "sign out")
    @ShellMethodAvailability("isUserSignedIn")
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @ShellMethod(value = "Shows account information when signed in", key = "describe account")
    public String describeAccount() {
        if (isUserSignedIn().isAvailable()) {
            return "Signed in with privileged account "
                    + SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            return "You are not signed in";
        }
    }
}
