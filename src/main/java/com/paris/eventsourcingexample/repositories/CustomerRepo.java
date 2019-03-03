package com.paris.eventsourcingexample.repositories;

import com.paris.eventsourcingexample.entities.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepo extends PagingAndSortingRepository<Customer, Long> {
}
