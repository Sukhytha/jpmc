package com.jpmc.theater.service;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Pricing;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.*;

@PrepareForTest({ReservationService.class,PricingService.class})
public class ReservationServiceTests
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ReservationService reservationService;
    private PricingService pricingService;
    private Pricing pricing;
    private Reservation reservation;
    private Customer customer;
    private Showing showing;

    @BeforeEach
    public void setUp()
    {
        reservationService = ReservationService.getInstance();
        pricingService = Mockito.mock(PricingService.class);
        pricing = Mockito.mock(Pricing.class);
        Whitebox.setInternalState(reservationService,"pricingService", pricingService);
        customer = Mockito.mock(Customer.class);
        showing = Mockito.mock(Showing.class);
    }

    @AfterEach
    public void tearDown()
    {
        //do clean up here.
    }

    @Test
    void testCalculateTotal() throws Exception
    {
        reservation =spy(new Reservation(customer,showing,10));
        when(pricing.getDiscountApplied()).thenReturn(5.5);
        when(pricing.getTotalPrice()).thenReturn(75.01);
        doReturn(pricing).when(pricingService).getPricing(any(Reservation.class));
        Whitebox.invokeMethod(reservationService,"calculateTotal", reservation);
        Assertions.assertEquals(5.5, reservation.getDiscountApplied(), "discountApplied ");
        Assertions.assertEquals(75.01, reservation.getTotalPrice(), "totalPrice ");
    }

    @Test
    void testReserve() throws Exception
    {
        when(pricing.getDiscountApplied()).thenReturn(5.0);
        when(pricing.getTotalPrice()).thenReturn(95.0);
        doReturn(pricing).when(pricingService).getPricing(any(Reservation.class));
        reservation = reservationService.reserve(customer,showing,10);
        Assertions.assertEquals(10, reservation.getAudienceCount(), "audience count");
        Assertions.assertEquals(customer, reservation.getCustomer(), "customer ");
        Assertions.assertEquals(showing, reservation.getShowing(), "showing ");
    }

}