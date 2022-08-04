package com.jpmc.theater.print;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jpmc.theater.model.Showing;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Printable that prints in pretty json format
 */
public class JsonPrinter implements Printable
{
    private static Logger logger = Logger.getLogger(JsonPrinter.class);

    /**
     * Prints the schedule in Json format
     * @param schedule
     */
    @Override
    public void printSchedule(List<Showing> schedule)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(schedule);
        JsonElement je = JsonParser.parseString(jsonString);
        System.out.println("====================Json Format====================");
        System.out.println(gson.toJson(je));
        if(logger.isDebugEnabled()) {
            logger.debug("Json format printed successfully");
        }
    }

}