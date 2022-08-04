package com.jpmc.theater.service;

import com.jpmc.theater.exception.BadRequestException;
import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Pricing;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;

/**
 * Service class that handles reservation related operations.
 */
public class ReservationService
{
    private static ReservationService soleInstance;
    private PricingService pricingService;
    public static ReservationService getInstance()
    {
        if(soleInstance==null)
        {
            synchronized (ShowService.class)
            {
                if(soleInstance==null)
                {
                    soleInstance = new ReservationService();
                }
            }
        }
        return soleInstance;
    }

    private ReservationService()
    {
        pricingService = PricingService.getInstance();
    }

    /**
     * Creates a reservation object based on the customer, showing and number of tickets requested.
     * Enhances the reservation object with information like pricing/discount and returns the updated reservation object.
     * @param customer
     * @param showing
     * @param howManyTickets
     * @return
     */
    public Reservation reserve(Customer customer, Showing showing, int howManyTickets) throws BadRequestException
    {
        Reservation reservation = new Reservation(customer, showing, howManyTickets);
        calculateTotal(reservation);
        return reservation;
    }

    private void calculateTotal(Reservation reservation)
    {
        Pricing pricing = pricingService.getPricing(reservation);
        reservation.setDiscountApplied(pricing.getDiscountApplied());
        reservation.setTotalPrice(pricing.getTotalPrice());
    }
}