package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }
}
