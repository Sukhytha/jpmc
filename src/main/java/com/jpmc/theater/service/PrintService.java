package com.jpmc.theater.service;

import com.jpmc.theater.model.Showing;
import com.jpmc.theater.print.HumanReadablePrinter;
import com.jpmc.theater.print.JsonPrinter;
import com.jpmc.theater.print.Printable;

import java.util.List;

/**
 * Service class that handles the printing of data
 */
public class PrintService
{
    private static List<Printable> printables;
    private static PrintService soleInstance;

    public static PrintService getInstance()
    {
        if(soleInstance==null)
        {
            synchronized (PrintService.class)
            {
                if(soleInstance==null)
                {
                    soleInstance = new PrintService();
                }
            }
        }
        return soleInstance;
    }
    private PrintService()
    {
        printables = loadPrintables();
    }
    private List<Printable> loadPrintables()
    {
        printables = List.of(
                new HumanReadablePrinter(),
                new JsonPrinter()
        );
        return printables;
    }

    /**
     * Prints through all the available printables.
     * @param schedules
     */
    public void print(List<Showing> schedules)
    {
        for(Printable printable: printables)
        {
            if(printable.isEnabled())
            {
                printable.printSchedule(schedules);
            }
        }
    }

}