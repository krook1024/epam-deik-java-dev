package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommandHandler;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Collection;

@ShellComponent
@ShellCommandGroup("Authentication Commands")
public class AuthCommandHandler extends SecuredCommandHandler {
    AuthenticationManager authenticationManager;
    UserService userService;

    public AuthCommandHandler(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @ShellMethod(value = "Signs up as a regular user", key = "sign up")
    public void register(String name, String password) {
        userService.createUser(name, password);
    }

    @ShellMethod(value = "Attempts a login as a regular user", key = "sign in")
    @ShellMethodAvailability("isUserSignedOut")
    public String login(String name, String password) {
        return attemptLogin(name, password);
    }

    @ShellMethod(value = "Attempts a login as an admin", key = "sign in privileged")
    @ShellMethodAvailability("isUserSignedOut")
    public String loginPrivileged(String name, String password) {
        return attemptLogin(name, password);
    }

    @ShellMethod(value = "Logs out", key = "sign out")
    @ShellMethodAvailability("isUserSignedIn")
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @ShellMethod(value = "Shows account information when signed in", key = "describe account")
    public String describeAccount() {
        if (isUserSignedIn().isAvailable()) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

            if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "Signed in with privileged account "
                        + SecurityContextHolder.getContext().getAuthentication().getName();
            }

            return "Signed in with account "
                    + SecurityContextHolder.getContext().getAuthentication().getName();
        }

        return "You are not signed in";
    }

    private String attemptLogin(String name, String password) {
        Authentication req = new UsernamePasswordAuthenticationToken(name, password);

        try {
            Authentication result = authenticationManager.authenticate(req);
            SecurityContextHolder.getContext().setAuthentication(result);
            return null;
        } catch (AuthenticationException e) {
            return "Login failed due to incorrect credentials";
        }
    }
}
