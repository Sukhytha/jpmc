package com.jpmc.theater.model;


import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Showing model
 */
public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public double getMovieFee() {
        return movie.getTicketPrice();
    }
    public String getMovieTitle() {
        return movie.getTitle();
    }

    public Duration getMovieRunningTime()
    {
        return movie.getRunningTime();
    }

    public boolean hasMovieSpecialCode()
    {
        return movie.isSpecialCode();
    }

    private void setMovie(Movie movie) {
        this.movie = movie;
    }

}