package com.epam.training.ticketservice.presentation.cli.configuration;

import com.epam.training.ticketservice.presentation.cli.io.InputReader;
import org.jline.reader.LineReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class CliConfiguration {

    @Bean
    public InputReader inputReader(@Lazy LineReader lineReader) {
        // param 1 lazy because The dependencies of some of the beans in the application context form a cycle:
        return new InputReader(lineReader);
    }
}
