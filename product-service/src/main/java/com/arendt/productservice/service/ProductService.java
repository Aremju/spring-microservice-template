package com.arendt.productservice.service;

import com.arendt.productservice.model.Product;
import com.arendt.productservice.model.ProductRequest;
import com.arendt.productservice.model.ProductResponse;
import com.arendt.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(final ProductRequest productRequest) {
        final var product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();

        productRepository.save(product);
        log.info("Product {} has been saved.", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        final var products = productRepository.findAll();

        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }
}
