package com.jpmc.theater.model;

/**
 * Reservation model
 */
public class Reservation
{
    private int reservationNo;
    private Customer customer;
    private Showing showing;
    private int audienceCount;
    private double totalPrice;
    private double discountApplied;

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscountApplied() {
        return discountApplied;
    }

    public void setDiscountApplied(double discountApplied) {
        this.discountApplied = discountApplied;
    }

    public Reservation(Customer customer, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
    }
    public Showing getShowing()
    {
        return showing;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public int getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(int audienceCount) {
        this.audienceCount = audienceCount;
    }


}