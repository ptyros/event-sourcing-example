package com.paris.eventsourcingexample.services.crud;

import com.paris.eventsourcingexample.entities.Product;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface Products {

    Product createProduct(Product product);

    Product updateProduct(Product product) throws EntityNotFoundException;

    void removeProduct(Long id) throws EntityNotFoundException;

    Optional<Product> retrieveProduct(Long id);

    List<Product> retrieveAllProducts();

}
