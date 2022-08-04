package com.jpmc.theater.rules;

import com.jpmc.theater.model.Showing;

/**
 *  Rule to allow discounts for certain showings based on the sequence number
 */
public class ShowSequenceDiscountRule extends DiscountRule implements ProductDiscountRules
{

    private int showSequence;

    public ShowSequenceDiscountRule(Integer seq, Boolean isDiscountPercentage, Double discount)
    {
        this.showSequence=seq;
        setDiscount(discount);
        setisDiscountPercentage(isDiscountPercentage);
    }

    /**
     * Method returns true if the showing's sequence matches the sequence of the day
     * @param showing
     * @return
     */
    @Override
    public boolean isApplicable(Showing showing)
    {
        return showSequence == showing.getSequenceOfTheDay();
    }

    /**
     * @return
     */
    public int getShowSequence() {
        return showSequence;
    }

}