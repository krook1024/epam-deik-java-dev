package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CliUserDetailsService implements UserDetailsService {
    private UserService userService;

    public CliUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findByName(name);

        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(name);
        builder.password(user.getPassword());

        if (user.getIsAdmin()) {
            builder.roles("USER", "ADMIN");
        } else {
            builder.roles("USER");
        }

        return builder.build();
    }
}
