package com.paris.eventsourcingexample.services.crud.impl;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.repositories.CustomerRepo;
import com.paris.eventsourcingexample.services.crud.Customers;
import com.paris.eventsourcingexample.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("customers")
@Slf4j
public class CustomersImpl implements Customers {

    private final CustomerRepo customerRepo;

    @Autowired
    public CustomersImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer createCustomer(Customer customer) {
        log.info("Entering createCustomer for customer: {}", customer);
        Customer entity = Customer.builder()
                                  .name(customer.getName())
                                  .balance(customer.getBalance())
                                  .build();
        Customer persisted = customerRepo.save(entity);

        log.info("Persisted new Customer: {}", persisted);
        return persisted;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer updateCustomer(Customer customer) throws EntityNotFoundException {
        log.info("Entering updateCustomer for customer: {}", customer);
        if(!customerRepo.existsById(customer.getCustomerId())) {
            EntityNotFoundException e = new EntityNotFoundException("Customer with id" +
                                                                    customer.getCustomerId() +
                                                                    Constants.NOT_FOUND);
            log.error("Failed to update Customer: {}", customer, e);
            throw e;
        }

        Customer persisted = customerRepo.save(customer);

        log.info("Updated Customer: {}", persisted);
        return persisted;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCustomer(Long id) throws EntityNotFoundException {
        log.info("Entering removeCustomer with id: {}", id);
        Customer entity = customerRepo.findById(id)
                                      .orElseThrow(() -> {
                                          EntityNotFoundException e =
                                                  new EntityNotFoundException("Customer with id" + id + Constants.NOT_FOUND);
                                          log.error("Failed to remove Customer with id: {}", id, e);
                                          return e;
                                      });
        customerRepo.delete(entity);
        log.info("Deleted Customer: {}", entity);
    }

    @Override
    public Optional<Customer> retrieveCustomer(Long id) {
        return customerRepo.findById(id);
    }

    @Override
    public List<Customer> retrieveAllCustomers() {
        return IteratorUtils.toList(customerRepo.findAll().iterator());
    }
}
