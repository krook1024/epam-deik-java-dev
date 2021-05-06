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
@AllArgsConstructor
@NoArgsConstructor
public class MovieProjection {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String title;

    private String genre;

    private int length;
}
