package com.epam.training.ticketservice.dataaccess.projection;

import javax.persistence.*;

@Entity
public class MovieProjection {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String genre;

    private int length;

    public MovieProjection() {

    }

    public MovieProjection(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
