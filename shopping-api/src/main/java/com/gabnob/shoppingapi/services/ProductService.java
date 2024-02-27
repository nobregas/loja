package com.gabnob.shoppingapi.services;

import com.gabnob.shoppingclient.dtos.ProductDTO;
import com.gabnob.shoppingclient.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Value("${PRODUCT_API_URL:http://localhost:8081}")
    private String productApiURL;

    public ProductDTO getProductByIdentifier(String productIdentifier) {
        try {
            WebClient webClient = WebClient.builder()
                    .baseUrl(productApiURL)
                    .build();

            Mono<ProductDTO> product = webClient.get()
                    .uri("/product/"+productIdentifier)
                    .retrieve()
                    .bodyToMono(ProductDTO.class);

            return product.block();
        } catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }





}
