package com.epam.training.ticketservice.dataaccess.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class RoomProjection {
    @Id
    @GeneratedValue
    private Long id;

    String name;

    @Column(name = "rowcount")
    int rows;

    int cols;

    public RoomProjection(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }
}
