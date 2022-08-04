package com.jpmc.theater.service;

import com.jpmc.theater.exception.NotFoundException;
import com.jpmc.theater.model.Movie;

import java.time.Duration;
import java.util.List;

/**
 * Service class that handles the operations on movie data.
 */
public class MovieService
{
    private List<Movie> movies;
    private static MovieService soleInstance;
    public static MovieService getInstance()
    {
        if(soleInstance==null)
        {
            synchronized (MovieService.class)
            {
                if(soleInstance==null)
                {
                    soleInstance = new MovieService();
                }
            }
        }
        return soleInstance;
    }
    private MovieService()
    {
        // The movies are hardcoded and loaded here.
        // But they can alternatively be loaded from config files, database etc.
        movies = List.of(
                new Movie(1,"Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                new Movie(2,"Turning Red", Duration.ofMinutes(85), 11, 0),
                new Movie(3,"The Batman", Duration.ofMinutes(95), 9, 0)
        );
    }

    /**
     * Returns the list of movies loaded in the system
     * @return
     */
    public List<Movie> getMovies()
    {
        return movies;
    }

    /**
     * Returns the movie for the id.
     * Raises a NotFoundException if an invalid id is supplied.
     * @param id
     * @return
     */
    public Movie getMovie(int id) throws NotFoundException
    {
        for(Movie movie: movies)
        {
            if(movie.isId(id))
            {
                return movie;
            }
        }
        throw new NotFoundException();
    }
}