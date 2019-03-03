package com.paris.eventsourcingexample.web.crud;

import com.paris.eventsourcingexample.entities.Order;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.services.crud.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crud/orders")
public class OrdersController {

    private final Orders orders;

    @Autowired
    public OrdersController(Orders orders) {
        this.orders = orders;
    }

    @PostMapping(consumes = "application/json")
    public Order createOrder(@RequestBody Order order) throws EntityNotFoundException {
        return orders.createOrder(order);
    }

    @PutMapping(consumes = "application/json")
    public Order updateOrder(@RequestBody Order order) throws EntityNotFoundException {
        return orders.updateOrder(order);
    }

    @DeleteMapping(value = "/{id}")
    public void removeOrder(@PathVariable Long id) throws EntityNotFoundException {
        orders.removeOrder(id);
    }

    @GetMapping(value = "/{id}")
    public Order retrieveOrder(@PathVariable Long id) {
        return orders.retrieveOrder(id).orElse(null);
    }

    @GetMapping
    public List<Order> retrieveAllOrders() {
        return orders.retrieveAllOrders();
    }
}
