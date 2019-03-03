package com.paris.eventsourcingexample.services.impl;

import com.paris.eventsourcingexample.entities.Customer;
import com.paris.eventsourcingexample.entities.Order;
import com.paris.eventsourcingexample.entities.Product;
import com.paris.eventsourcingexample.entities.enumerations.OrderStatus;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.services.SampleDataGenerator;
import com.paris.eventsourcingexample.services.crud.Customers;
import com.paris.eventsourcingexample.services.crud.Orders;
import com.paris.eventsourcingexample.services.crud.Products;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("sampleDataGenerator")
@Slf4j
public class SampleDataGeneratorImpl implements SampleDataGenerator {

    private final Customers customers;

    private final Products products;

    private final Orders orders;

    private static final String PARIS = "Paris";

    @Autowired
    public SampleDataGeneratorImpl(Customers customers, Products products,
                                   Orders orders) {
        this.customers = customers;
        this.products = products;
        this.orders = orders;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSampleData() throws EntityNotFoundException {
        log.info("Entering insertSampleData");

        clearAllData();

        List<Customer> allCustomers = insertCustomers();
        List<Product> allProducts = insertProducts();
        insertOrders(allCustomers, allProducts);
    }

    @Override
    public void clearOrders() throws EntityNotFoundException {
        log.info("Entering clearOrders");
        for(Order order : orders.retrieveAllOrders()) {
            orders.removeOrder(order.getOrderId());
        }
    }

    @Override
    public void clearAllData() throws EntityNotFoundException {
        log.info("Entering clearAllData");

        clearOrders();

        for(Product product: products.retrieveAllProducts()) {
            products.removeProduct(product.getProductId());
        }

        for(Customer customer: customers.retrieveAllCustomers()) {
            customers.removeCustomer(customer.getCustomerId());
        }
    }

    private List<Customer> insertCustomers() {
        List<Customer> entities = new ArrayList<>();
        Customer paris = Customer.builder()
                                 .name(PARIS)
                                 .balance(BigDecimal.valueOf(1000L))
                                 .build();

        Customer george = Customer.builder()
                                 .name("George")
                                 .balance(BigDecimal.valueOf(700.55))
                                 .build();

        Customer nick = Customer.builder()
                                 .name("Nick")
                                 .balance(BigDecimal.valueOf(200.22))
                                 .build();

        entities.add(customers.createCustomer(paris));
        entities.add(customers.createCustomer(george));
        entities.add(customers.createCustomer(nick));

        return entities;
    }

    private List<Product> insertProducts() {
        List<Product> entities = new ArrayList<>();

        Product product = Product.builder()
                                 .cost(BigDecimal.TEN)
                                 .build();
        entities.add(products.createProduct(product));

        product.setProductId(null);
        product.setCost(BigDecimal.valueOf(20L));
        entities.add(products.createProduct(product));

        product.setProductId(null);
        product.setCost(BigDecimal.valueOf(50L));
        entities.add(products.createProduct(product));

        product.setProductId(null);
        product.setCost(BigDecimal.valueOf(100L));
        entities.add(products.createProduct(product));

        return entities;
    }

    private List<Order> insertOrders(List<Customer> allCustomers, List<Product> allProducts) throws EntityNotFoundException {
        List<Order> entities = new ArrayList<>();

        Order order = Order.builder()
                           .quantity(3L)
                           .status(OrderStatus.PLACED)
                           .product(allProducts.stream()
                                               .filter(p -> p.getCost().compareTo(BigDecimal.TEN) == 0)
                                               .findFirst()
                                               .orElse(null))
                           .customer(allCustomers.stream()
                                                 .filter(c -> c.getName().equals(PARIS))
                                                 .findFirst()
                                                 .orElse(null))
                           .build();
        entities.add(orders.createOrder(order));

        Order.builder()
             .quantity(2L)
             .status(OrderStatus.PROCESSED)
             .product(allProducts.stream()
                                 .filter(p -> p.getCost().compareTo(BigDecimal.valueOf(50L)) == 0)
                                 .findFirst()
                                 .orElse(null))
             .customer(allCustomers.stream()
                                   .filter(c -> c.getName().equals(PARIS))
                                   .findFirst()
                                   .orElse(null))
             .build();
        entities.add(orders.createOrder(order));

        return entities;
    }
}
