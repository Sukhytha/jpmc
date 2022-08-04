package com.jpmc.theater.service;

import com.jpmc.theater.exception.NotFoundException;
import com.jpmc.theater.model.Customer;
import java.util.List;

/**
 * Service class that handles the operations on customer data
 */
public class CustomerService
{
    private static CustomerService soleInstance;

    private List<Customer> customers;

    public static CustomerService getInstance()
    {
        if(soleInstance==null)
        {
            synchronized (CustomerService.class)
            {
                if(soleInstance==null)
                {
                    soleInstance = new CustomerService();
                }
            }
        }
        return soleInstance;
    }
    private CustomerService()
    {
        customers = List.of(
                new Customer("John Doe", "id00001"),
                new Customer("Meghan K", "id00002"),
                new Customer("Sue Lou",  "id00003")
                );
    }

    /**
     * Returns the customer with the given customer id in the system.
     * If not found raises a NotFoundException
     * @param id
     * @return
     * @throws NotFoundException
     */
    public Customer getCustomer(String id) throws NotFoundException
    {
        for(Customer customer: customers)
        {
            if(customer.isId(id))
            {
                return customer;
            }
        }
        throw new NotFoundException();
    }

    /**
     * returns the list of customers in the system
     * @return
     */
    public List<Customer> getCustomers()
    {
        return customers;
    }

}