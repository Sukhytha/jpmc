package com.jpmc.theater.rules;

import com.jpmc.theater.model.Showing;

import java.time.LocalDateTime;

/**
 * Rule to allow discounts for certain showings based on the show hours
 */
public class ShowHourDiscountRule extends DiscountRule implements ProductDiscountRules
{
    private LocalDateTime showTimeAfter;
    private LocalDateTime showTimeBefore;

    /**
     *
     * @param showing
     * @return
     */
    @Override
    public boolean isApplicable(Showing showing)
    {

        boolean isApplicable = false;
        if(showTimeAfter!=null)
        {
            isApplicable = !showing.getStartTime().isBefore(showTimeAfter);
        }
        if(showTimeBefore!=null)
        {
            isApplicable = isApplicable && !showing.getStartTime().isAfter(showTimeBefore);
        }
        return isApplicable;
    }

    /**
     *
     * @param showTimeAfter
     * @param showTimeBefore
     * @param isDiscountPercentage
     * @param discount
     */
    public ShowHourDiscountRule(LocalDateTime showTimeAfter,LocalDateTime showTimeBefore,Boolean isDiscountPercentage, Double discount)
    {
        this.showTimeAfter = showTimeAfter;
        this.showTimeBefore = showTimeBefore;
        setisDiscountPercentage(isDiscountPercentage);
        setDiscount(discount);
    }

    /**
     *
     * @return
     */
    public LocalDateTime getShowTimeAfter() {
        return showTimeAfter;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getShowTimeBefore() {
        return showTimeBefore;
    }

}