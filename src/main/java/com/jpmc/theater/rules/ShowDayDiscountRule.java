package com.jpmc.theater.rules;

import com.jpmc.theater.model.Showing;

/**
 * Rule to allow discounts for certain showings based on the show day
 */
public class ShowDayDiscountRule extends DiscountRule implements ProductDiscountRules
{
    private int dayOfTheMonth;

    public ShowDayDiscountRule(Integer dayOfTheMonth, Boolean isDiscountPercentage, Double discount)
    {
        this.dayOfTheMonth=dayOfTheMonth;
        setisDiscountPercentage(isDiscountPercentage);
        setDiscount(discount);
    }

    /**
     * Returns true if the day of the show matches the day configured for discount.
     * @param showing
     * @return
     */
    @Override
    public boolean isApplicable(Showing showing)
    {
        return showing.getStartTime().getDayOfMonth()==dayOfTheMonth;
    }

    /**
     * returns the configured day of the month
     * @return
     */
    public int getDayOfTheMonth() {
        return dayOfTheMonth;
    }

}