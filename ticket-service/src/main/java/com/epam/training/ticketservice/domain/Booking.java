package com.epam.training.ticketservice.domain;

import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Booking {

    private final Screening screening;

    private final User user;

    private final List<Seat> seats;

    @Value("${ticket-service.base-ticket-price}")
    private static int screeningPrice;

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
        sb.append(" for " + (screeningPrice * seats.size()) + " HUF");

        return sb.toString();
    }
}
