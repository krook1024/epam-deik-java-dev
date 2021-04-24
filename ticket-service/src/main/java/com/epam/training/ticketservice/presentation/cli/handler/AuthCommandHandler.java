package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.auth.SecuredCommand;
import com.epam.training.ticketservice.presentation.cli.io.InputReader;
import com.epam.training.ticketservice.service.UserService;
import org.jline.reader.LineReader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.util.StringUtils;

@ShellComponent
public class AuthCommandHandler extends SecuredCommand {
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

    @ShellMethod(value = "Attempts a login as admin", key = "sign in priviliged")
    @ShellMethodAvailability("isUserSignedOut")
    public String loginPrivileged() {
        String name;
        boolean nameInvalid = true;

        do {
            name = inputReader.prompt("Please enter your username");

            if (StringUtils.hasText(name)) {
                nameInvalid = false;
            } else {
                System.out.println("Username cannot be empty");
            }
        } while (nameInvalid);

        String password;
        boolean passwordInvalid = true;

        do {
            password = inputReader.prompt("Please enter your password", false);

            if (StringUtils.hasText(password)) {
                passwordInvalid = false;
            } else {
                System.out.println("Password cannot be empty");
            }
        } while (passwordInvalid);

        Authentication req = new UsernamePasswordAuthenticationToken(name, password);

        try {
            Authentication result = authenticationManager.authenticate(req);
            SecurityContextHolder.getContext().setAuthentication(result);
            return "Logged in successfully";
        } catch (AuthenticationException e) {
            return "Couldn't log you in: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Logs out", key = "sign out")
    @ShellMethodAvailability("isUserSignedIn")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "Logged out successsfully";
    }

    @ShellMethod(value = "Shows account information when signed in", key = "describe account")
    @ShellMethodAvailability("isUserSignedIn")
    public String describeAccount() {
        return "You are currently logged in as " + SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
