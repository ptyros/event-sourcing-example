package com.paris.eventsourcingexample.services;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;

import java.math.BigDecimal;

public interface CustomerBalanceManager {

    Customer topUpCustomer(Long customerId, BigDecimal amount) throws EntityNotFoundException;

    Customer setBalance(Long customerId, BigDecimal amount) throws EntityNotFoundException;

}
