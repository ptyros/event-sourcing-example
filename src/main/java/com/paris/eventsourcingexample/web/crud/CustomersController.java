package com.paris.eventsourcingexample.web.crud;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.services.crud.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crud/customers")
public class CustomersController {

    private final Customers customers;

    @Autowired
    public CustomersController(Customers customers) {
        this.customers = customers;
    }

    @PostMapping(consumes = "application/json")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customers.createCustomer(customer);
    }

    @PutMapping(consumes = "application/json")
    public Customer updateCustomer(@RequestBody Customer customer) throws EntityNotFoundException {
        return customers.updateCustomer(customer);
    }

    @DeleteMapping(value = "/{id}")
    public void removeCustomer(@PathVariable Long id) throws EntityNotFoundException {
        customers.removeCustomer(id);
    }

    @GetMapping(value = "/{id}")
    public Customer retrieveCustomer(@PathVariable Long id) {
        return customers.retrieveCustomer(id).orElse(null);
    }

    @GetMapping
    public List<Customer> retrieveAllCustomers() {
        return customers.retrieveAllCustomers();
    }
}
