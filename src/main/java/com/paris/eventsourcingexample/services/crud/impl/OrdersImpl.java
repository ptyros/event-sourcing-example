package com.paris.eventsourcingexample.services.crud.impl;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.entities.Order;
import com.paris.eventsourcingexample.entities.Product;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.repositories.OrderRepo;
import com.paris.eventsourcingexample.services.crud.Customers;
import com.paris.eventsourcingexample.services.crud.Orders;
import com.paris.eventsourcingexample.services.crud.Products;
import com.paris.eventsourcingexample.utils.Constants;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("orders")
@Slf4j
public class OrdersImpl implements Orders {

    private final OrderRepo orderRepo;

    private final Products products;

    private final Customers customers;

    @Autowired
    public OrdersImpl(OrderRepo orderRepo, Products products, Customers customers) {
        this.orderRepo = orderRepo;
        this.products = products;
        this.customers = customers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Order order) throws EntityNotFoundException {
        log.info("Entering createOrder for order: {}", order);
        Tuple2<Product, Customer> tuple = retrieveRelatedData(order.getCustomer().getCustomerId(), order.getProduct().getProductId());

        Order entity = Order.builder()
                            .quantity(order.getQuantity())
                            .product(tuple._1())
                            .customer(tuple._2())
                            .status(order.getStatus())
                            .build();
        Order persisted = orderRepo.save(entity);

        log.info("Persisted new Order: {}", persisted);
        return persisted;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order updateOrder(Order order) throws EntityNotFoundException {
        log.info("Entering updateOrder for order: {}", order);

        if(!orderRepo.existsById(order.getOrderId())) {
            EntityNotFoundException e = new EntityNotFoundException("Order with id" +
                                                                    order.getOrderId() +
                                                                    Constants.NOT_FOUND);
            log.error("Failed to update Order: {}", order, e);
            throw e;
        }

        Tuple2<Product, Customer> tuple = retrieveRelatedData(order.getCustomer().getCustomerId(), order.getProduct().getProductId());
        order.setProduct(tuple._1());
        order.setCustomer(tuple._2());

        Order persisted = orderRepo.save(order);

        log.info("Updated Order: {}", persisted);
        return persisted;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOrder(Long id) throws EntityNotFoundException {
        log.info("Entering removeOrder with id: {}", id);
        Order entity = orderRepo.findById(id)
                                .orElseThrow(() -> {
                                          EntityNotFoundException e =
                                                  new EntityNotFoundException("Order with id" + id + Constants.NOT_FOUND);
                                          log.error("Failed to remove Order with id: {}", id, e);
                                          return e;
                                      });
        orderRepo.delete(entity);
        log.info("Deleted Order: {}", entity);
    }

    @Override
    public Optional<Order> retrieveOrder(Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public List<Order> retrieveAllOrders() {
        return IteratorUtils.toList(orderRepo.findAll().iterator());
    }

    @Override
    public Tuple2<Product, Customer> retrieveRelatedData(Long customerId, Long productId) throws EntityNotFoundException {

        Product product = products.retrieveProduct(productId)
                                  .orElseThrow(() -> {
                                      EntityNotFoundException e =
                                              new EntityNotFoundException("Product with id: " + productId + Constants.NOT_FOUND);
                                      log.error("Failed to create new Order", e);

                                      return e;
                                  });

        Customer customer = customers.retrieveCustomer(customerId)
                                     .orElseThrow(() -> {
                                         EntityNotFoundException e =
                                                 new EntityNotFoundException("Customer with id: " + customerId + Constants.NOT_FOUND);
                                         log.error("Failed to create new Order", e);

                                         return e;
                                     });

        return new Tuple2<>(product, customer);
    }
}
