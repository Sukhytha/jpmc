package com.jpmc.theater;

import com.jpmc.theater.exception.BadRequestException;
import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.service.CustomerService;
import com.jpmc.theater.service.PrintService;
import com.jpmc.theater.service.ReservationService;
import com.jpmc.theater.service.ShowService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Main class, that is the entry point to the theatre application.
 * Exposes to printSchedule and make reservation
 */
public class Theater{

    /*Add logging . Both error and debug. We can decide on the what we want to use. for now I've used log4j
    *
     */
    private static Logger logger = Logger.getLogger(Theater.class);

    private ShowService showService;
    private CustomerService customerService;
    private ReservationService reservationService;

    private PrintService printService;
    public Theater()
    {
        showService = ShowService.getInstance();
        reservationService = ReservationService.getInstance();
        printService = PrintService.getInstance();
        customerService = CustomerService.getInstance();
    }

    /**
     *
     * @param customerId
     * @param showSequence
     * @param howManyTickets
     * @return
     * @throws BadRequestException
     */
    public Reservation reserve(String customerId, int showSequence, int howManyTickets) throws BadRequestException
    {
        if(logger.isDebugEnabled()) {
            logger.debug("About to reserve for customer "+customerId+ ", show sequence "+showSequence+", tickets "+howManyTickets);
        }
        try
        {
            Customer customer = customerService.getCustomer(customerId);
            Showing showing = showService.getShowing(showSequence);
            return reservationService.reserve(customer, showing, howManyTickets);
        }
        catch(Exception e)
        {
            logger.error("Unable to successfully create reservation"+e);
            throw new BadRequestException();
        }
    }

    /**
     *
     */
    public void printSchedule()
    {
        if(logger.isDebugEnabled()) {
            logger.debug("About to print schedule");
        }

        List<Showing> schedule = showService.getSchedule();
        printService.print(schedule);

        if(logger.isDebugEnabled()) {
            logger.debug("Printed schedules successfully");
        }
    }

    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        Theater theatre = new Theater();
        theatre.printSchedule();
    }
}