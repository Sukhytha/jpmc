package com.jpmc.theater.service;

import com.jpmc.theater.Theater;
import com.jpmc.theater.exception.JpmcTheaterException;
import com.jpmc.theater.model.Pricing;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.provider.LocalDateProvider;
import com.jpmc.theater.rules.ProductDiscountRules;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Service class that calculates the pricing for each reservation based on the discount rules configured in the system.
 * Service is capable of loading as many of the following rules as set up in the properties file,rulesConfig.properties.
 * The discounts and constraints for each of the rules can be modified from the config files.
 * Rules(of the below list) can be added, updated or removed using the config file.
 *      ShowSequenceDiscountRule. Key in the property file "showSequenceDiscountRules".
 *      ShowDayDiscountRules Key in the property file "showDayDiscountRules".
 *      ShowHourDiscountRule Key in the property file "showHourDiscountRules".
 *      MovieSpecialDiscountRule Key in the property file "movieSpecialDiscountRules".
 *      The properties for each are seperated using "," .
 *      To create multiple instances of each rule use "|" as seperator.
 *      Example has been given in the rulesConfig.properties
 */
public class PricingService
{
    private static Logger logger = Logger.getLogger(PricingService.class);
    private List<ProductDiscountRules> productDiscountRules = new ArrayList<>();

    private static PricingService soleInstance;

    public static PricingService getInstance()
    {
        if(soleInstance==null)
        {
            synchronized (ShowService.class)
            {
                if(soleInstance==null)
                {
                    soleInstance = new PricingService();
                }
            }
        }
        return soleInstance;
    }

    private PricingService()
    {
        Properties properties = new Properties();
        InputStream is=null;
        try //try with resources can be used
        {
            //load a properties file from class path, inside static method
            ClassLoader configClassLoader = Theater.class.getClassLoader();
            is = configClassLoader.getResourceAsStream("rulesConfig.properties");
            if (is != null) {
                properties.load(is);
            }
            loadShowSequenceDiscountRules(properties);
            loadShowHourDiscountRules(properties);
            loadShowDayDiscountRules(properties);
            loadMovieSpecialDiscountRules(properties);
        }
        catch (IOException | ClassNotFoundException| InvocationTargetException| InstantiationException|
               IllegalAccessException|NoSuchMethodException e) {
            //done like this for simplicity/demonstration purpose.
            // we can create custom exception if we wish to handle them differently/
            // or add extra information for the caller than what standard java has to offer.
            //There is no need to create custom exceptions if there is no value in doing so.
            logger.error(e);
            throw new JpmcTheaterException();
        }
        finally
        {
            if(is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

    }

    /**
     * Calculates the pricing details and sets the totalDiscount and totalprice on the reservation object
     * @param reservation
     * @return
     */
    public Pricing getPricing(Reservation reservation)
    {
        double unitDiscount = getUnitDiscount(reservation);
        double totalDiscount = unitDiscount* reservation.getAudienceCount();
        double totalMovieFee = reservation.getShowing().getMovieFee() * reservation.getAudienceCount();
        double totalPrice = totalMovieFee-totalDiscount;
        return new Pricing(totalDiscount,totalPrice);
    }
    private double getUnitDiscount(Reservation reservation)
    {
        return calculateDiscount(reservation.getShowing());
    }
    private double calculateDiscount(Showing showing)
    {
        double deepestDiscount = 0;
        for (ProductDiscountRules productDiscountRule : productDiscountRules) {
            if (productDiscountRule.isApplicable(showing))
            {
                double discountAmt = productDiscountRule.getDiscount();

                if (productDiscountRule.isDiscountPercentage()) {
                    discountAmt = showing.getMovieFee() * (productDiscountRule.getDiscount());
                }
                if (productDiscountRule.isStackable()) { //currently none of the discounts are stackable. But in future if we wish to honor multiple discounts, this could be used.
                    discountAmt += discountAmt;
                }
                deepestDiscount= Math.max(deepestDiscount, discountAmt); //gets the deepest discount of the applicable discounts
            }
        }
        return deepestDiscount;
    }

    /**
     * Creates instances of the ShowSequenceDiscountRule and adds it to the productDiscountRules list.
     * Creates as many rules as set up in the properties file for the key "showSequenceDiscountRules" .
     * To create multiple instances use "|" as seperator.
     *
     * @param properties
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void loadShowSequenceDiscountRules(Properties properties) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        String[] ruleProperties = loadProperties(properties,"showSequenceDiscountRules");
        if(ruleProperties!=null) {
            for (String ruleProperty : ruleProperties) {
                String[] props = ruleProperty.split(",");
                Constructor<ProductDiscountRules> construct = (Constructor<ProductDiscountRules>) Class.forName("com.jpmc.theater.rules.ShowSequenceDiscountRule").getDeclaredConstructor(Integer.class, Boolean.class, Double.class);
                productDiscountRules.add(construct.newInstance(Integer.valueOf(props[0]), Boolean.valueOf(props[1]), Double.valueOf(props[2])));
            }
        }
    }

    /**
     * Creates instances of the ShowDayDiscountRules and adds it to the productDiscountRules list.
     * Creates as many rules as set up in the properties file for the key "showDayDiscountRules" .
     * To create multiple instances use "|" as seperator.
     *
     * @param properties
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void loadShowDayDiscountRules(Properties properties) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        String[] ruleProperties = loadProperties(properties,"showDayDiscountRules");
        if(ruleProperties!=null)
        {
            for (String ruleProperty : ruleProperties)
            {
                String[] props = ruleProperty.split(",");
                Constructor<ProductDiscountRules> construct = (Constructor<ProductDiscountRules>) Class.forName("com.jpmc.theater.rules.ShowDayDiscountRule").getDeclaredConstructor(Integer.class, Boolean.class, Double.class);
                productDiscountRules.add(construct.newInstance(Integer.valueOf(props[0]), Boolean.valueOf(props[1]), Double.valueOf(props[2])));
            }
        }
    }

    /**
     * Creates instances of the MovieSpecialDiscountRule and adds it to the productDiscountRules list.
     * Creates as many rules as set up in the properties file for the key "movieSpecialDiscountRules" .
     * To create multiple instances use "|" as seperator.
     *
     * @param properties
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void loadMovieSpecialDiscountRules(Properties properties) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        String[] ruleProperties = loadProperties(properties,"movieSpecialDiscountRules");
        if(ruleProperties!=null) {
            for (String ruleProperty : ruleProperties) {
                String[] props = ruleProperty.split(",");
                Constructor<ProductDiscountRules> construct = (Constructor<ProductDiscountRules>) Class.forName("com.jpmc.theater.rules.MovieSpecialDiscountRule").getDeclaredConstructor(Integer.class, Boolean.class, Double.class);
                productDiscountRules.add(construct.newInstance(Integer.valueOf(props[0]), Boolean.valueOf(props[1]), Double.valueOf(props[2])));
            }
        }
    }

    /**
     * Creates instances of the ShowHourDiscountRules and adds it to the productDiscountRules list.
     * Creates as many rules as set up in the properties file for the key "showHourDiscountRules" .
     * To create multiple instances use "|" as seperator.
     *
     * @param properties
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private void loadShowHourDiscountRules(Properties properties) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        String[] ruleProperties = loadProperties(properties,"showHourDiscountRules");
        if(ruleProperties!=null) {
            for (String ruleProperty : ruleProperties) {
                String[] props = ruleProperty.split(",");
                LocalDateTime after = LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(Integer.valueOf(props[0]), Integer.valueOf(props[1])));
                LocalDateTime before = LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(Integer.valueOf(props[2]), Integer.valueOf(props[3])));
                Constructor<ProductDiscountRules> construct = (Constructor<ProductDiscountRules>) Class.forName("com.jpmc.theater.rules.ShowHourDiscountRule").getDeclaredConstructor(LocalDateTime.class, LocalDateTime.class, Boolean.class, Double.class);
                productDiscountRules.add(construct.newInstance(after, before, Boolean.valueOf(props[4]), Double.valueOf(props[5])));
            }
        }
    }

    /**
     * helper class to load the properties from the properties file.
     * @param properties
     * @param propertiesName
     * @return
     */
    private String[] loadProperties(Properties properties, String propertiesName)
    {
        String rules = properties.getProperty(propertiesName);
        if(rules!=null) {
            return rules.split("\\|");
        }
        return null;
    }

    void setSoleInstanceForTesting(PricingService newValue)
    {
        soleInstance = newValue;
    }
}