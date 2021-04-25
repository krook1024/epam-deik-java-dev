package com.epam.training.ticketservice.dataaccess.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScreeningProjection {

    @EmbeddedId
    EmbeddedScreeningId id;
}
