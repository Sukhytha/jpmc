package com.jpmc.theater.model;

import java.util.Objects;

/**
 * customer model
 */
public class Customer {

    private String name;

    private String id;

    /**
     * @param name customer name
     * @param id customer id
     */
    public Customer(String name, String id) {
        this.id = id;
        this.name = name;

        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "name: " + name;
    }

    public boolean isId(String id)
    {
        return this.id.equals(id);
    }

}