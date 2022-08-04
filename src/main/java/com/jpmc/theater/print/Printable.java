package com.jpmc.theater.print;

import com.jpmc.theater.model.Showing;

import java.util.List;

/**
 * Interface for print providers
 */
public interface Printable
{
    /**
     * Prints the list of showings supplied.
     * @param schedule
     */
    public void printSchedule(List<Showing> schedule);

    /**
     * By default all the printables are enabled.
     * System can be made flexible by reading from property file to disable certain printables
     * @return boolean
     */
    public default boolean isEnabled()
    {
        return true;
    }
}