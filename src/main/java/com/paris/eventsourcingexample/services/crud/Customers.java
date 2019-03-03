package com.paris.eventsourcingexample.services.crud;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface Customers {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer) throws EntityNotFoundException;

    void removeCustomer(Long id) throws EntityNotFoundException;

    Optional<Customer> retrieveCustomer(Long id);

    List<Customer> retrieveAllCustomers();

}
