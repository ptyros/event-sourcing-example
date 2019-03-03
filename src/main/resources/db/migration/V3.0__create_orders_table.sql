CREATE TABLE orders (
  order_id   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  quantity BIGINT NOT NULL,
  status VARCHAR(50) NOT NULL,
  fk_product_id BIGINT NOT NULL,
  fk_customer_id BIGINT NOT NULL,
  CONSTRAINT orders_products_id_fk FOREIGN KEY (fk_product_id) REFERENCES products(product_id),
  CONSTRAINT orders_customers_id_fk FOREIGN KEY (fk_customer_id) REFERENCES customers(customer_id)
);