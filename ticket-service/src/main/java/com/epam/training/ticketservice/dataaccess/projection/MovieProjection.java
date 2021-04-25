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

    public MovieProjection(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }
}
