package com.arendt.productservice;

import com.arendt.productservice.model.Product;
import com.arendt.productservice.model.ProductRequest;
import com.arendt.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/products: should be CREATED successfully when correct object passed")
    void shouldCreateProduct_whenPassedCorrectly() throws Exception {
        final var expectedSize = 1;
        final var productRequestObject = getTestProductRequest();
        final var mockRequest = MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestObject));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
        assertThat(productRepository.findAll())
                .hasSize(expectedSize);
    }

    @Test
    @DisplayName("GET /api/products: given one element in database, should return object as list and ok")
    void shouldReturnOkAndElement_whenInsideDatabase() throws Exception {
        final var product = getTestProduct();
        productRepository.save(product);
        final var mockRequest = MockMvcRequestBuilders.get("/api/products")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("%s%s%s", "[", objectMapper.writeValueAsString(product), "]")));
    }

    private ProductRequest getTestProductRequest() {
        return new ProductRequest(
                "IPhone 15 Pro Max",
                "Very nice IPhone, finally with USB-C cable",
                1999.0
        );
    }

    private Product getTestProduct() {
        return new Product(
                "some-id",
                "IPhone 14",
                "weaker",
                2.0
        );
    }
}
