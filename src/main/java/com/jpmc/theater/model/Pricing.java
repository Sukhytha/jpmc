package com.jpmc.theater.model;

/**
 * Basic pricing model.
 */
public class Pricing
{
    private double discountApplied;

    private double totalPrice;

    /**
     *
     * @return
     */
    public double getDiscountApplied() {
        return discountApplied;
    }

    /**
     *
     * @return
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @param discountApplied
     * @param totalPrice
     */
    public Pricing(double discountApplied, double totalPrice)
    {
        this.discountApplied = discountApplied;
        this.totalPrice = totalPrice;
    }
}