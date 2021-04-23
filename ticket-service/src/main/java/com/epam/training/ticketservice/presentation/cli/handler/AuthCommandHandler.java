package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.UserService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AuthCommandHandler {
    private UserService userService;

    public AuthCommandHandler(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(value = "Attempts a login as admin", key = "sign in priviliged")
    public String login() {
        return "Failed";
    }
}
