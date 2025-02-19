package com.example.circuit.productService;

import com.example.circuit.commonDto.ProductDto;
import com.example.circuit.commonDto.ProductRatingDto;
import com.example.circuit.entity.Product;
import com.example.circuit.ratingService.RatingServiceClient;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class ProductService {

    private final RatingServiceClient ratingServiceClient;
    private final ExecutorService executorService;

    private Map<Integer, Product> db;

    @PostConstruct
    private void init(){
        this.db = Map.of(
                1, Product.of(1, "Blood On The Dance Floor", 12.45),
                2, Product.of(2, "The Eminem Show", 12.12)
        );
    }

    /*public CompletionStage<ProductRatingDto> getProductDto(int productId){
        var product = CompletableFuture.supplyAsync(() -> this.db.get(productId), executorService);
        var rating = this.ratingServiceClient.getProductRatingDto(productId);
        //return product.thenCombine(rating, (p, r) -> ProductDto.of(productId, p.getDescription(), p.getPrice(), r));
        return rating;
    }*/

    public CompletableFuture<ProductDto> getProductDto(int productId){
        // assuming this is a DB call
        var product = CompletableFuture.supplyAsync(() -> this.db.get(productId), executorService);
        var rating = this.ratingServiceClient.getProductRatingDto(productId);
        return product.thenCombine(rating, (p, r) -> ProductDto.of(productId, p.getDescription(), p.getPrice(), r));
    }



}
