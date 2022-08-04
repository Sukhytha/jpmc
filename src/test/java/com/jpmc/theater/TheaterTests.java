package com.jpmc.theater;

import com.jpmc.theater.exception.BadRequestException;
import com.jpmc.theater.model.Reservation;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterTests
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    Theater theater;

    @BeforeEach
    public void setUp()
    {
        theater = new Theater();
    }

    @AfterEach
    public void tearDown()
    {
        //do clean up here.
    }
    @Test
    void testTotalFeeForCustomer_sunnyDay() throws BadRequestException
    {
        Reservation reservation = theater.reserve("id00001", 2, 4);
        assertEquals(37.5, reservation.getTotalPrice());
        assertEquals(12.5,reservation.getDiscountApplied());
    }

    @Test
    void testPrintMovieSchedule() {

        theater.printSchedule();
    }
}