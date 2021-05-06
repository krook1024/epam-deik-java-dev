package com.epam.training.ticketservice.dataaccess.projection;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingProjection {

    @ManyToOne
    ScreeningProjection screeningProjection;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private UserProjection userProjection;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<SeatProjection> seats;
}
