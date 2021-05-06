package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class ScreeningProjection {

    @EmbeddedId
    EmbeddedScreeningId id;
}
