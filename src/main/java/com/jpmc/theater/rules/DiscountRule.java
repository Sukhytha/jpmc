package com.jpmc.theater.rules;

/**
 * Abstract class for all discount rules.
 * Contains discount percentage indicator and the discount
 *
 */
public abstract class DiscountRule
{
    private boolean isDiscountPercentage;
    private double discount;

    public boolean isDiscountPercentage()
    {
        return isDiscountPercentage;
    }
    public double getDiscount()
    {
        return discount;
    }

    protected void setisDiscountPercentage(boolean isDiscountPercentage)
    {
        this.isDiscountPercentage = isDiscountPercentage;
    }

    protected void setDiscount(double discount)
    {
        this.discount = discount;
    }

}