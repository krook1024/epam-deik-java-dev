package com.epam.training.ticketservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Booking {
    private final Screening screening;

    private final User user;

    private final List<Seat> seats;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Seats ");
        Iterator<Seat> seatIterator = seats.iterator();
        while (seatIterator.hasNext()) {
            Seat seat = seatIterator.next();

            sb.append(seat);

            if (seatIterator.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append(" on " + screening.getMovie().getTitle());
        sb.append(" in room " + screening.getRoom().getName());
        sb.append(" at " + screening.getStartTime());
        sb.append(" for " + (1500 * seats.size()) + " HUF");

        return sb.toString();
    }
}
