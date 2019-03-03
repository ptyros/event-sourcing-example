package com.paris.eventsourcingexample.web.crud;

import com.paris.eventsourcingexample.entities.Product;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.services.crud.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crud/products")
public class ProductsController {

    private final Products products;

    @Autowired
    public ProductsController(Products products) {
        this.products = products;
    }

    @PostMapping(consumes = "application/json")
    public Product createProduct(@RequestBody Product product) {
        return products.createProduct(product);
    }

    @PutMapping(consumes = "application/json")
    public Product updateProduct(@RequestBody Product product) throws EntityNotFoundException {
        return products.updateProduct(product);
    }

    @DeleteMapping(value = "/{id}")
    public void removeProduct(@PathVariable Long id) throws EntityNotFoundException {
        products.removeProduct(id);
    }

    @GetMapping(value = "/{id}")
    public Product retrieveProduct(@PathVariable Long id) {
        return products.retrieveProduct(id).orElse(null);
    }

    @GetMapping
    public List<Product> retrieveAllProducts() {
        return products.retrieveAllProducts();
    }
}
