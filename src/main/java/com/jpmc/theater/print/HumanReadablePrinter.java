package com.jpmc.theater.print;

import com.jpmc.theater.provider.LocalDateProvider;
import com.jpmc.theater.model.Showing;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Printable class that prints in pretty human readable format
 */
public class HumanReadablePrinter implements Printable
{
    private static Logger logger = Logger.getLogger(HumanReadablePrinter.class);

    /**
     * Prints the schdule in human readable format
     * @param schedule
     */
    @Override
    public void printSchedule(List<Showing> schedule) {
        System.out.println(LocalDateProvider.singleton().currentDate());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovieTitle() + " " + humanReadableFormat(s.getMovieRunningTime()) + " $" + s.getMovieFee())
        );
        System.out.println("===================================================");

        if(logger.isDebugEnabled()) {
            logger.debug("Human readable format printed successfully");
        }
    }

    private String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }


    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

}