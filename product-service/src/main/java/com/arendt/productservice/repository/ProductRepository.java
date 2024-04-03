package com.arendt.productservice.repository;

import com.arendt.productservice.model.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
