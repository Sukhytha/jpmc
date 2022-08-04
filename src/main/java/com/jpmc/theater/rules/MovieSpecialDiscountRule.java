package com.jpmc.theater.rules;

import com.jpmc.theater.model.Showing;

/**
 * Rule to allow discounts for certain movies based on special code configured
 */
public class MovieSpecialDiscountRule extends DiscountRule implements ProductDiscountRules
{
    private int movieSpecialCode;
    //currently not used but in future if we need to have different discounts for
    //differrent movie code this can be used.

    /**
     *  This discount is applicable if the movie contains special code
     * @param showing
     * @return
     */
    @Override
    public boolean isApplicable(Showing showing)
    {
        return showing.hasMovieSpecialCode();
    }


    public MovieSpecialDiscountRule(Integer movieSpecialCode, Boolean isDiscountPercentage, Double discount)
    {
        this.movieSpecialCode=movieSpecialCode;
        setisDiscountPercentage(isDiscountPercentage);
        setDiscount(discount);
    }
}