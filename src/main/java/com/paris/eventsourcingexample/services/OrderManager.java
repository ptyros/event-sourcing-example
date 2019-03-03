package com.paris.eventsourcingexample.services;

import com.paris.eventsourcingexample.entities.Order;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.exceptions.NotEnoughBalanceException;

public interface OrderManager {

    Order placeOrder(Long customerId, Long productId, Long quantity) throws EntityNotFoundException, NotEnoughBalanceException;

    Order processOrder(Long orderId) throws EntityNotFoundException;

    Order deliverOrder(Long orderId) throws EntityNotFoundException;

}
