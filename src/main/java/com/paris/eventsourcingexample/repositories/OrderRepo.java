package com.paris.eventsourcingexample.repositories;

import com.paris.eventsourcingexample.entities.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepo extends PagingAndSortingRepository<Order, Long> {
}
