package se.iths.webshopclient.business.service;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.iths.webshopclient.business.model.Product;

import java.util.ArrayList;
import java.util.List;

public class WebClientService {

    static WebClient client = WebClient.create("http://localhost:8080");

    public WebClientService (){}

    public List<Product> findAll() {
        Flux<Product> flux = client
                .get()
                .uri("/rs/product/all")
                .retrieve()
                .bodyToFlux(Product.class);

        return flux.collectList().block();
    }

    public Product findByID(Long id) {
        try {
            Mono<Product> mono = client
                    .get()
                    .uri("/rs/product/id/{id}", id)
                    .retrieve()
                    .bodyToMono(Product.class);

            return mono.block();
        } catch (WebClientResponseException e) {
            return null;
        }

    }

    public List<Product> findByName(String name) {
        Flux<Product> flux = client
                .get()
                .uri("/rs/product/name/{name}", name)
                .retrieve()
                .bodyToFlux(Product.class);

        return flux.collectList().block();
    }


    public List<Product> findByCategory(String category) {
        Flux<Product> flux = client
                .get()
                .uri("/rs/product/category/{category}", category)
                .retrieve()
                .bodyToFlux(Product.class);

        return flux.collectList().block();
    }


    public Product createProduct(String name, String category, Double price, String description) {
        Mono<Product> mono = client
                .post()
                .uri("/rs/product/create")
                .bodyValue(new Product(name, category, price, description))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class);
        return mono.block();
    }

    public Product updateName(Long id, String name) {
        try {
            Mono<Product> mono = client
                    .put()
                    .uri("/rs/product/update/id/{id}/name/{name}", id, name)
                    .retrieve()
                    .bodyToMono(Product.class);
            return mono.block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    public Product updateCategory(Long id, String category) {
        try {
            Mono<Product> mono = client
                    .put()
                    .uri("/rs/product/update/id/{id}/category/{category}", id, category)
                    .retrieve()
                    .bodyToMono(Product.class);

            return mono.block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    public Product updatePrice(Long id, Double price) {
        try {
            Mono<Product> mono = client
                    .put()
                    .uri("/rs/product/update/id/{id}/price/{price}", id, price)
                    .retrieve()
                    .bodyToMono(Product.class);

            return mono.block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    public Product updateDescription(Long id, String description) {
        try {
            Mono<Product> mono = client
                    .put()
                    .uri("/rs/product/update/id/{id}/description/{description}", id, description)
                    .retrieve()
                    .bodyToMono(Product.class);

            return mono.block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }

    public List<Product> deleteProduct(Long id) {
        try {
            Flux<Product> flux = client
                    .delete()
                    .uri("/rs/product/delete/id/{id}", id)
                    .retrieve()
                    .bodyToFlux(Product.class);

            return flux.collectList().block();
        } catch (WebClientResponseException e) {
            return new ArrayList<>();
        }
    }
}
