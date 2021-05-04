package com.epam.training.ticketservice.dataaccess.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingProjection {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    ScreeningProjection screeningProjection;

    @ManyToOne
    private UserProjection userProjection;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<SeatProjection> seats;
}
