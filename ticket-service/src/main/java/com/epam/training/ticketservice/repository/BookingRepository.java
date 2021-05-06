package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Booking;
import com.epam.training.ticketservice.domain.Screening;
import java.util.List;

public interface BookingRepository {

    void saveBooking(Booking booking);

    List<Booking> findAllByScreening(Screening screening);

    List<Booking> findByUserName(String userName);
}
