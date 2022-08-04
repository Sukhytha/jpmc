package com.jpmc.theater.service;

import com.jpmc.theater.exception.NotFoundException;
import com.jpmc.theater.provider.LocalDateProvider;
import com.jpmc.theater.model.Showing;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class that handles the scheduling of shows
 */
public class ShowService
{
    private static List<Showing> schedule;

    private MovieService movieService;

    private static ShowService soleInstance;
    public static ShowService getInstance()
    {
        if(soleInstance==null)
        {
            synchronized (ShowService.class)
            {
                if(soleInstance==null)
                {
                    soleInstance = new ShowService();
                }
            }
        }
        return soleInstance;
    }


    private ShowService()
    {
        movieService = MovieService.getInstance();
        // The showings are hardcoded and loaded here.
        // But they can be alternatively be loaded from config files, database etc.
        schedule = List.of(
                new Showing(movieService.getMovie(1), 1, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(9, 0))),
                new Showing(movieService.getMovie(1), 2, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(11, 0))),
                new Showing(movieService.getMovie(2), 3, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(12, 50))),
                new Showing(movieService.getMovie(2), 4, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(14, 30))),
                new Showing(movieService.getMovie(1), 5, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(16, 10))),
                new Showing(movieService.getMovie(3), 6, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(17, 50))),
                new Showing(movieService.getMovie(2), 7, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(19, 30))),
                new Showing(movieService.getMovie(1), 8, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(21, 10))),
                new Showing(movieService.getMovie(3), 9, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(23, 0)))
        );
    }

    /**
     * Returns the list of showings configured in the system
     * @return
     */
    public List<Showing> getSchedule()
    {
        return schedule;
    }

    /**
     * Returns the showing for the sequence number.
     * Raises a NotFoundException if the there are no showings for the sequence number
     * @param sequence
     * @return
     * @throws NotFoundException
     */
    public Showing getShowing(int sequence) throws NotFoundException
    {
        List<Showing> schedule = getSchedule();
        for(Showing showing:schedule)
        {
            if(showing.isSequence(sequence))
            {
                return showing;
            }
        }
        throw new NotFoundException();
    }
}