package com.jpmc.theater.model;

import java.time.Duration;
import java.util.Objects;

/**
 * Movie model
 */
public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;

    private int id;
    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(int id, String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.id = id;
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
    public boolean isId(int id) {
        return this.id == id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
    // Not sure if we need to include fields like ticket price, running time etc?

    public boolean isSpecialCode()
    {
        return MOVIE_CODE_SPECIAL==specialCode;
    }

}