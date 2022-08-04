package com.jpmc.theater.service;

import com.jpmc.theater.model.Movie;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class MovieServiceTests
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    MovieService movieService;

    @BeforeEach
    public void setUp()
    {
        movieService = Mockito.mock(MovieService.class);

        List<Movie> testMovieLoad =
                List.of(
                        new Movie(1,"Cars 4", Duration.ofMinutes(90), 12.5, 1),
                        new Movie(2,"Turning Red", Duration.ofMinutes(85), 11, 0),
                        new Movie(3,"The Batman", Duration.ofMinutes(95), 9, 0)
                );
        Whitebox.setInternalState(movieService,"movies",testMovieLoad);

    }

    @AfterEach
    public void tearDown()
    {
        //do clean up here.
    }

    @Test
    void testGetMovie()
    {
        Mockito.doCallRealMethod().when(movieService).getMovie(1);
        Movie movie = movieService.getMovie(1);
        assertTrue("Movie id is equal :",movie.isId(1));
        assertEquals("Movie Name:","Cars 4",movie.getTitle());
        assertTrue("Movie Running Time is as expected:",Duration.ofMinutes(90).equals(movie.getRunningTime()));
        assertEquals("Ticket price:",12.5,movie.getTicketPrice(),0);

    }

    @Test
    void testGetMovies()
    {
        Mockito.doCallRealMethod().when(movieService).getMovies();
        List<Movie> movies = movieService.getMovies();
        assertNotNull(movies);
        assertEquals(3, movies.size());
    }
}