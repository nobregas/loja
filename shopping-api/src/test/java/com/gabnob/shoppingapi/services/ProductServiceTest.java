package com.gabnob.shoppingapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabnob.shoppingclient.dtos.ProductDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    public static MockWebServer mockBackEnd;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        ReflectionTestUtils.setField(productService, "productApiURL", baseUrl);
    }

    @Test
    void test_getProductByIdentifier() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPreco(100F);
        productDTO.setProductIdentifier("prod-identifier");

        ObjectMapper objectMapper = new ObjectMapper();

        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(productDTO))
                .addHeader("Content-Type", "application/json"));

        productDTO = productService.getProductByIdentifier("prod-identifier");

        Assertions.assertEquals(100F, productDTO.getPreco());
        Assertions.assertEquals("prod-identifier", productDTO.getProductIdentifier());


    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }
}