package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.dao.ScreeningDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaScreeningRepository implements ScreeningRepository {

    ScreeningDao screeningDao;

    public JpaScreeningRepository(ScreeningDao screeningDao) {
        this.screeningDao = screeningDao;
    }
}
