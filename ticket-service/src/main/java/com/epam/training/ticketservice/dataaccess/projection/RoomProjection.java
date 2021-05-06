package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomProjection {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    String name;

    @Column(name = "rowCount")
    int rows;

    int cols;

    public RoomProjection(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }
}
