package com.epam.training.ticketservice.dataaccess.projection;

import com.epam.training.ticketservice.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScreeningProjection {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private MovieProjection movieProjection;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private RoomProjection roomProjection;

    private Date start;
}
