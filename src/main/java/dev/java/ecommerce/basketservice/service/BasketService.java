package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.dto.request.BasketRequestDTO;
import dev.java.ecommerce.basketservice.dto.response.PlatziProductResponseDTO;
import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.entity.Product;
import dev.java.ecommerce.basketservice.entity.Status;
import dev.java.ecommerce.basketservice.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final ProductService productService;
    private final BasketRepository basketRepository;

    public Optional<Basket> findBasketById(String id) {
        return basketRepository.findById(id);
    }

    public Basket createBasket(BasketRequestDTO basketRequest) {

        basketRepository.findByClientAndStatus(basketRequest.clientId(), Status.OPEN)
                .ifPresent(basket -> {
                    throw new IllegalArgumentException("There is already an open basket for this client");
                });

        List<Product> products = new ArrayList<>();
        basketRequest.products().forEach(productRequest -> {
            PlatziProductResponseDTO platziProductResponse = productService.getProductById(productRequest.id());
            products.add(Product.builder()
                    .id(platziProductResponse.id())
                    .title(platziProductResponse.title())
                    .price(platziProductResponse.price())
                    .quantity(productRequest.quantity())
                    .build());

        });
        Basket newBasket = Basket.builder()
                .client(basketRequest.clientId())
                .status(Status.OPEN)
                .products(products)
                .build();

        newBasket.calculateTotalPrice();
        return basketRepository.save(newBasket);
    }

}
