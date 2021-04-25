package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private final String name;
    private final String password;
    private final Boolean isAdmin;
}
