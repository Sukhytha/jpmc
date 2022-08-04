package com.jpmc.theater.service.provider;

import com.jpmc.theater.provider.LocalDateProvider;
import org.junit.jupiter.api.Test;

public class LocalDateProviderTests {

    /**
     * This test has to be enhanced to check for current time by supplying our own fixed "clock".
     * Sysout should be removed.
     */
    @Test
    void makeSureCurrentTime() {
        System.out.println("current time is - " + LocalDateProvider.singleton().currentDate());
    }
}