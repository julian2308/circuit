package com.example.circuit.ratingService;

import com.example.circuit.commonDto.ProductRatingDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class RatingServiceClient {

    private static final Logger log = LoggerFactory.getLogger(RatingServiceClient.class);
    private final RestClient client;
    private final ExecutorService executorService;

    @CircuitBreaker(name = "getProductRatingDto", fallbackMethod = "onError")
    public CompletionStage<ProductRatingDto> getProductRatingDto(int productId) {
        return CompletableFuture.supplyAsync(() -> this.getRating(productId), executorService);
    }

    private ProductRatingDto getRating(int productId){
        try {
            return this.client.get()
                    .uri("http://localhost:8080/ratings/{productId}", productId)
                    .retrieve()
                    .body(ProductRatingDto.class);
        }
            catch (Exception e){
            throw new RuntimeException("Error al obtener el rating del producto", e);
        }
    }

    private CompletionStage<ProductRatingDto> onError(Throwable throwable) {
        log.error("error: {}", throwable.getMessage());
        return CompletableFuture.completedFuture(ProductRatingDto.of(0, Collections.emptyList()));
    }
}
