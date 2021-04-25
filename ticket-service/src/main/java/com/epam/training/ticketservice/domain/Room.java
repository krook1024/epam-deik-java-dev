package com.epam.training.ticketservice.domain;

public class Room {
    private String name;
    private int rows;
    private int cols;

    public Room(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    @Override
    public String toString() {
        return "Room " + name + " with " + rows * cols + " seats, " + rows + " rows and " + cols + " columns";
    }
}
