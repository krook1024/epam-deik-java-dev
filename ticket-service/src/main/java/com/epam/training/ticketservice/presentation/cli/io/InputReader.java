package com.epam.training.ticketservice.presentation.cli.io;


import org.jline.reader.LineReader;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.util.StringUtils;

public class InputReader {
    private static final Character DEFAULT_MASK = '*';
    private Character mask;
    private LineReader reader;

    public InputReader(LineReader reader) {
        this.reader = reader;
    }

    public InputReader(LineReader reader, Character mask) {
        this.reader = reader;
        this.mask = mask != null ? mask : DEFAULT_MASK;
    }

    public String prompt(String prompt) {
        return prompt(prompt, "", true);
    }

    public String prompt(String prompt, Boolean echo) {
        return prompt(prompt, "", echo);
    }

    public String prompt(String prompt, String defaultValue, Boolean echo) {
        String answer;

        if (echo) {
            answer = reader.readLine(prompt + ": ");
        } else {
            answer = reader.readLine(prompt + ": ", mask);
        }

        if (StringUtils.hasLength(answer)) {
            answer = defaultValue;
        }

        return answer;
    }
}
