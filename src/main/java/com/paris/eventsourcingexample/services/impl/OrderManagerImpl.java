package com.paris.eventsourcingexample.services.impl;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.entities.Order;
import com.paris.eventsourcingexample.entities.Product;
import com.paris.eventsourcingexample.entities.enumerations.OrderStatus;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.exceptions.NotEnoughBalanceException;
import com.paris.eventsourcingexample.services.OrderManager;
import com.paris.eventsourcingexample.services.crud.Customers;
import com.paris.eventsourcingexample.services.crud.Orders;
import com.paris.eventsourcingexample.utils.Constants;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.function.Supplier;

@Service("orderManager")
@Slf4j
public class OrderManagerImpl implements OrderManager {

    private final Orders orders;

    private final Customers customers;

    @Autowired
    public OrderManagerImpl(Orders orders, Customers customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public Order placeOrder(Long customerId,
                            Long productId,
                            Long quantity) throws EntityNotFoundException, NotEnoughBalanceException {
        log.info("Entering placeOrder with customerId: {}, productId: {} and quantity: {}", customerId, productId, quantity);

        Tuple2<Product, Customer> tuple = orders.retrieveRelatedData(customerId, productId);
        Product product = tuple._1();
        Customer customer = tuple._2();
        BigDecimal totalCost = product.getCost().multiply(BigDecimal.valueOf(quantity), Constants.DEFAULT_MATH_CONTEXT);
        if(customer.getBalance().compareTo(totalCost) < 0) {
            NotEnoughBalanceException e = new NotEnoughBalanceException("Customer with id: " +
                                                                        customerId +
                                                                        " has not enough balance in order to place an order for Product with id: " +
                                                                        productId +
                                                                        " and quantity" + quantity);
            log.error("Failed to Place Order", e);

            throw e;
        }
        customer.setBalance(customer.getBalance().subtract(totalCost, Constants.DEFAULT_MATH_CONTEXT));
        Customer persistedCustomer = customers.updateCustomer(customer);
        Order order = orders.createOrder(Order.builder()
                                              .quantity(quantity)
                                              .customer(persistedCustomer)
                                              .product(product)
                                              .status(OrderStatus.PLACED)
                                              .build());

        log.info("Successfully placed Order: {}", order);
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public Order processOrder(Long orderId) throws EntityNotFoundException {
        log.info("Entering processOrder with orderId: {}", orderId);
        Order order = orders.retrieveOrder(orderId)
                            .orElseThrow(orderNotFoundExceptionSupplier(orderId));

        if(!OrderStatus.PLACED.equals(order.getStatus())) {
            throw orderNotFoundExceptionSupplier(orderId).get();
        }
        order.setStatus(OrderStatus.PROCESSED);

        Order savedOrder = orders.createOrder(order);
        log.info("Successfully processed Order: {}", savedOrder);
        return savedOrder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public Order deliverOrder(Long orderId) throws EntityNotFoundException {
        log.info("Entering deliverOrder with orderId: {}", orderId);
        Order order = orders.retrieveOrder(orderId)
                            .orElseThrow(orderNotFoundExceptionSupplier(orderId));

        if(!OrderStatus.PROCESSED.equals(order.getStatus())) {
            throw orderNotFoundExceptionSupplier(orderId).get();
        }
        order.setStatus(OrderStatus.DELIVERED);

        Order savedOrder = orders.createOrder(order);
        log.info("Successfully delivered Order: {}", savedOrder);
        return savedOrder;
    }

    private Supplier<EntityNotFoundException> orderNotFoundExceptionSupplier(Long orderId) {
        return () -> {
            EntityNotFoundException e = new EntityNotFoundException("Order With id: " + orderId + " not found or has wrong status");
          log.error("Failed to handle Order", e);

          return e;
        };
    }
}
