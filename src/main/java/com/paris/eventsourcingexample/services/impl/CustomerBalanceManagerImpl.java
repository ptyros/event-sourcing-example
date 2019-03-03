package com.paris.eventsourcingexample.services.impl;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.services.CustomerBalanceManager;
import com.paris.eventsourcingexample.services.crud.Customers;
import com.paris.eventsourcingexample.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("customerBalanceManager")
@Slf4j
public class CustomerBalanceManagerImpl implements CustomerBalanceManager {

    private final Customers customers;

    @Autowired
    public CustomerBalanceManagerImpl(Customers customers) {
        this.customers = customers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer topUpCustomer(Long customerId, BigDecimal amount) throws EntityNotFoundException {
        log.info("Entering topUpCustomer with customerId: {} and amount: {}", customerId, amount);
        Customer customer =
                customers.retrieveCustomer(customerId)
                         .orElseThrow(() -> {
                             EntityNotFoundException e =
                                     new EntityNotFoundException("Customer with id: " + customerId + Constants.NOT_FOUND);
                             log.error("Failed to create new Order", e);

                             return e;
                         });

        customer.setBalance(customer.getBalance().add(amount, Constants.DEFAULT_MATH_CONTEXT));
        Customer result = customers.updateCustomer(customer);

        log.info("Topped Up Customer: {}", result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer setBalance(Long customerId, BigDecimal amount) throws EntityNotFoundException {
        log.info("Entering setBalance with customerId: {} and amount: {}", customerId, amount);
        Customer customer =
                customers.retrieveCustomer(customerId)
                         .orElseThrow(() -> {
                             EntityNotFoundException e =
                                     new EntityNotFoundException("Customer with id: " + customerId + Constants.NOT_FOUND);
                             log.error("Failed to create new Order", e);

                             return e;
                         });

        customer.setBalance(amount.setScale(2,
                                            Constants.DEFAULT_MATH_CONTEXT.getRoundingMode()));
        Customer result = customers.updateCustomer(customer);

        log.info("Topped Up Customer: {}", result);
        return result;
    }
}
