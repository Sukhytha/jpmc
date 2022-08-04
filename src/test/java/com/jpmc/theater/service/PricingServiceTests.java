package com.jpmc.theater.service;

import com.jpmc.theater.Theater;
import com.jpmc.theater.model.*;
import com.jpmc.theater.provider.LocalDateProvider;
import com.jpmc.theater.rules.MovieSpecialDiscountRule;
import com.jpmc.theater.rules.ShowDayDiscountRule;
import com.jpmc.theater.rules.ShowSequenceDiscountRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.spy;

public class PricingServiceTests {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    PricingService pricingService;
    private Reservation reservation;
    private Customer customer;
    private Showing showing;

    private Movie movie;

    Properties properties;

    @BeforeEach
    public void setUp() throws IOException {

        pricingService = PricingService.getInstance();
        customer = spy(new Customer("TestCust Abc", "id00001"));
        InputStream is=null;
        try {
            //below lines are used for set up to test the loading of the rules.
            properties = new Properties();

            //load a properties file from class path, inside static method
            ClassLoader configClassLoader = Theater.class.getClassLoader();
            is = configClassLoader.getResourceAsStream("rulesConfigTest.properties");
            if (is != null) {
                properties.load(is);
            }
        }
        finally
        {
            if(is!=null)
            {
                try {
                    is.close();
                }catch(IOException e)
                {

                }
            }
        }
    }

    @AfterEach
    public void tearDown()
    {
        pricingService.setSoleInstanceForTesting(null);
        //do clean up here.
    }

    @Test
    void testLoadShowSequenceDiscountRules() throws Exception {
        Whitebox.setInternalState(pricingService, "productDiscountRules", new ArrayList());
        Whitebox.invokeMethod(pricingService, "loadShowSequenceDiscountRules", properties);
        Field field = PricingService.class.getDeclaredField("productDiscountRules");
        field.setAccessible(true);
        List<ShowSequenceDiscountRule> loadedList = (List<ShowSequenceDiscountRule>) field.get(pricingService);

        assertEquals(2, loadedList.size());
        assertEquals(3, loadedList.get(0).getDiscount());
        assertFalse(loadedList.get(0).isDiscountPercentage());
        assertFalse(loadedList.get(0).isStackable());

        assertEquals(2, loadedList.get(1).getDiscount());
        assertFalse(loadedList.get(1).isDiscountPercentage());
        assertFalse(loadedList.get(1).isStackable());
        assertEquals(2, loadedList.get(1).getShowSequence());
    }

    @Test
    void testLoadShowDayDiscountRules() throws Exception {
        Whitebox.setInternalState(pricingService, "productDiscountRules", new ArrayList());
        Whitebox.invokeMethod(pricingService, "loadShowDayDiscountRules", properties);
        Field field = PricingService.class.getDeclaredField("productDiscountRules");
        field.setAccessible(true);
        List<ShowDayDiscountRule> loadedList = (List<ShowDayDiscountRule>) field.get(pricingService);

        assertEquals(1, loadedList.size());
        assertEquals(1, loadedList.get(0).getDiscount());
        assertEquals(7, loadedList.get(0).getDayOfTheMonth());
        assertFalse(loadedList.get(0).isDiscountPercentage());
        assertFalse(loadedList.get(0).isStackable());

    }

    @Test
    void testLoadMovieSpecialDiscountRules() throws Exception {
        Whitebox.setInternalState(pricingService, "productDiscountRules", new ArrayList());
        Whitebox.invokeMethod(pricingService, "loadMovieSpecialDiscountRules", properties);
        Field field = PricingService.class.getDeclaredField("productDiscountRules");
        field.setAccessible(true);
        List<MovieSpecialDiscountRule> loadedList = (List<MovieSpecialDiscountRule>) field.get(pricingService);

        assertEquals(1, loadedList.size());
        assertEquals(0.2, loadedList.get(0).getDiscount());
        assertTrue(loadedList.get(0).isDiscountPercentage());
        assertFalse(loadedList.get(0).isStackable());

    }

    // deepest discount applied should be special movie discount 20%
    @Test
    void testGetPricing_movieSpecialApplied() throws Exception
    {
        movie = spy(new Movie(1,"My test Movie", Duration.ofMinutes(90), 15, 1));
        showing = spy(new Showing(movie, 1, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(9, 0))));
        reservation =spy(new Reservation(customer,showing,10));
        Pricing pricing = pricingService.getPricing(reservation);

        assertEquals(120,pricing.getTotalPrice());
        assertEquals(30,pricing.getDiscountApplied());
    }

    // deepest discount applied should be show hour discount 25%
    @Test
    void testGetPricing_showHourDiscountApplied() throws Exception
    {
        movie = spy(new Movie(1,"My test Movie", Duration.ofMinutes(90), 15, 1));
        showing = spy(new Showing(movie, 1, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(11, 0))));
        reservation =spy(new Reservation(customer,showing,10));
        Pricing pricing = pricingService.getPricing(reservation);
        // deepest discount should be show hour discount of 25%. applied = 37.5 .priceafterdiscount = 112.5
        assertEquals(112.5,pricing.getTotalPrice());
        assertEquals(37.5,pricing.getDiscountApplied());
    }

    //deepest discount applied should be the showsequence discount for seq1 = 3
    @Test
    void testGetPricing_showSequence1DiscountApplied() throws Exception
    {
        movie = spy(new Movie(1,"My test Movie", Duration.ofMinutes(90), 15, 0));
        showing = spy(new Showing(movie, 1, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(16, 30))));
        reservation =spy(new Reservation(customer,showing,10));
        Pricing pricing = pricingService.getPricing(reservation);
        // deepest discount is showsequence discount of 3 for sequence 1
        assertEquals(120,pricing.getTotalPrice());
        assertEquals(30,pricing.getDiscountApplied());
    }

    //deepest discount applied should be the showsequence discount for seq2 =2
    @Test
    void testGetPricing_showSequence2DiscountApplied() throws Exception
    {
        movie = spy(new Movie(1,"My test Movie", Duration.ofMinutes(90), 15, 0));
        showing = spy(new Showing(movie, 2, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(16, 30))));
        reservation =spy(new Reservation(customer,showing,10));
        Pricing pricing = pricingService.getPricing(reservation);
        // deepest discount is showsequence discount of 2 for sequence 2
        assertEquals(130,pricing.getTotalPrice());
        assertEquals(20,pricing.getDiscountApplied());
    }
}