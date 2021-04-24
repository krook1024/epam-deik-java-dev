package com.epam.training.ticketservice.auth;

import com.epam.training.ticketservice.service.CliUserDetailsService;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.Collections;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CliUserDetailsService(userService);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }
}
