package com.epam.training.ticketservice.domain;

import com.epam.training.ticketservice.presentation.cli.DateConverterComponent;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Booking {

    private final Screening screening;

    private final User user;

    private final List<Seat> seats;

    private static final int screeningPrice = 1500;

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

        sb.append(" on ").append(screening.getMovie().getTitle());
        sb.append(" in room ").append(screening.getRoom().getName());
        sb.append(" starting at ").append(new DateConverterComponent().convertBack(screening.getStartTime()));
        sb.append(" for ").append(screeningPrice * seats.size()).append(" HUF");

        return sb.toString();
    }
}
