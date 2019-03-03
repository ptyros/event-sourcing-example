package com.paris.eventsourcingexample.web;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.services.CustomerBalanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/customer-balance-manager")
public class CustomerBalanceManagerController {

    private final CustomerBalanceManager customerBalanceManager;

    @Autowired
    public CustomerBalanceManagerController(
            CustomerBalanceManager customerBalanceManager) {
        this.customerBalanceManager = customerBalanceManager;
    }

    @PostMapping(value = "/top-up/{customerId}/{amount}")
    public Customer topUpCustomer(@PathVariable Long customerId, @PathVariable BigDecimal amount) throws EntityNotFoundException {
        return customerBalanceManager.topUpCustomer(customerId, amount);
    }

    @PostMapping(value = "/balance/{customerId}/{amount}")
    public Customer setBalance(@PathVariable Long customerId, @PathVariable BigDecimal amount) throws EntityNotFoundException {
        return customerBalanceManager.setBalance(customerId, amount);
    }
}
