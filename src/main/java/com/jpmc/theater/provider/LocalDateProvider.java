package com.jpmc.theater.provider;

import java.time.LocalDate;

public class LocalDateProvider {
    private static LocalDateProvider instance = null;

    //private Clock clock; //we can use something like this for testability.

    /**
     * @return make sure to return singleton instance
     */
    public static LocalDateProvider singleton()
    {
        if (instance == null) {
            synchronized (LocalDateProvider.class) {
                if (instance == null) {
                    instance = new LocalDateProvider();
                }
            }
        }
            return instance;
    }

    /*private LocalDateProvider()
    {
        clock = Clock.systemDefaultZone();
    }*/

    public LocalDate currentDate() {
            //return LocalDate.now(clock);
            return LocalDate.now();
    }
}