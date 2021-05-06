package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SeatProjection {

    private int rowNum;
    private int colNum;
}
