package com.paris.eventsourcingexample.repositories;

import com.paris.eventsourcingexample.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepo extends PagingAndSortingRepository<Product, Long> {
}
