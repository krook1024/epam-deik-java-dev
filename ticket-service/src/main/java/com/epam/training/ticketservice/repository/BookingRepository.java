package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Booking;

import java.util.List;

public interface BookingRepository {

    void saveBooking(Booking booking);

    List<Booking> findByUsername(String name);
}
