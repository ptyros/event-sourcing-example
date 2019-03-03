package com.paris.eventsourcingexample.services.crud.impl;

import com.paris.eventsourcingexample.entities.Product;
import com.paris.eventsourcingexample.exceptions.EntityNotFoundException;
import com.paris.eventsourcingexample.repositories.ProductRepo;
import com.paris.eventsourcingexample.services.crud.Products;
import com.paris.eventsourcingexample.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("products")
@Slf4j
public class ProductsImpl implements Products {

    private final ProductRepo productRepo;

    @Autowired
    public ProductsImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product createProduct(Product product) {
        log.info("Entering createProduct for product: {}", product);
        Product entity = Product.builder()
                                .cost(product.getCost())
                                .build();
        Product persisted = productRepo.save(entity);

        log.info("Persisted new Product: {}", persisted);
        return persisted;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product updateProduct(Product product) throws EntityNotFoundException {
        log.info("Entering updateProduct for product: {}", product);
        if(!productRepo.existsById(product.getProductId())) {
            EntityNotFoundException e = new EntityNotFoundException("Product with id" +
                                                                    product.getProductId() +
                                                                    Constants.NOT_FOUND);
            log.error("Failed to update Product: {}", product, e);
            throw e;
        }

        Product persisted = productRepo.save(product);

        log.info("Updated Product: {}", persisted);
        return persisted;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeProduct(Long id) throws EntityNotFoundException {
        log.info("Entering removeProduct with id: {}", id);
        Product entity = productRepo.findById(id)
                                    .orElseThrow(() -> {
                                          EntityNotFoundException e =
                                                  new EntityNotFoundException("Product with id" + id + Constants.NOT_FOUND);
                                          log.error("Failed to remove Product with id: {}", id, e);
                                          return e;
                                      });
        productRepo.delete(entity);
        log.info("Deleted Product: {}", entity);
    }

    @Override
    public Optional<Product> retrieveProduct(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public List<Product> retrieveAllProducts() {
        return IteratorUtils.toList(productRepo.findAll().iterator());
    }
}
