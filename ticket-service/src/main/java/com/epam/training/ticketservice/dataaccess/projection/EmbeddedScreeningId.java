package com.epam.training.ticketservice.dataaccess.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddedScreeningId implements Serializable {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private MovieProjection movieProjection;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomProjection roomProjection;

    private Date startTime;
}
