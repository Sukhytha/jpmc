package com.jpmc.theater.rules;

import com.jpmc.theater.model.Showing;

/**
 * Interface for product discount rules
 */
public interface ProductDiscountRules
{
    /**
     * Determines if the rule is applicable for the show.
     * @param showing
     * @return
     */
    public boolean isApplicable(Showing showing);

    /**
     * Indicates if the configured discount is a percentage or the amount value.
     * True if configured discount is a percentage.
     * False if configured discount is an amount value.
     * @return
     */
    public boolean isDiscountPercentage();

    /**
      Returns if the rules are stackable, that is if they can be added with each other.
     * @return
     */
    //currently none of the discounts are stackable.
    // But in future if we wish to honor multiple discounts, this could be used.
    public default boolean isStackable()
    {
        return false;
    }

    /**
     * Returns the discount for the rule
     * @return
     */
    public double getDiscount();

}