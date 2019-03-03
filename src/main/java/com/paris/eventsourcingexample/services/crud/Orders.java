package com.paris.eventsourcingexample.services.crud;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.entities.Order;
import com.paris.eventsourcingexample.entities.Product;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import io.vavr.Tuple2;

import java.util.List;
import java.util.Optional;

public interface Orders {

    Order createOrder(Order order) throws EntityNotFoundException;

    Order updateOrder(Order order) throws EntityNotFoundException;

    void removeOrder(Long id) throws EntityNotFoundException;

    Optional<Order> retrieveOrder(Long id);

    List<Order> retrieveAllOrders();

    Tuple2<Product, Customer> retrieveRelatedData(Long customerId, Long productId) throws EntityNotFoundException;

}
