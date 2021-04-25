package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Screening;

import java.util.List;

public interface ScreeningRepository {
    void saveScreening(Screening screening);

    void deleteScreening(Screening screening);

    List<Screening> findAll();
}
