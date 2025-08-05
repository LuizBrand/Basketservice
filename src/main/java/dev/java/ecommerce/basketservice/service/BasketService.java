package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.dto.request.BasketRequestDTO;
import dev.java.ecommerce.basketservice.dto.request.PaymentRequest;
import dev.java.ecommerce.basketservice.dto.response.PlatziProductResponseDTO;
import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.entity.Product;
import dev.java.ecommerce.basketservice.entity.Status;
import dev.java.ecommerce.basketservice.exception.BasketNotFoundException;
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

    public Basket updateBasket(String basketId, BasketRequestDTO basketRequest) {
        Optional<Basket> optBasket = findBasketById(basketId);

        if (optBasket.isPresent()) {
            Basket savedBasket = optBasket.get();
            List<Product> products = new ArrayList<>();

            basketRequest.products().forEach(productRequestDTO -> {
                PlatziProductResponseDTO platziProductResponse = productService.getProductById(productRequestDTO.id());
                products.add(Product.builder()
                        .id(platziProductResponse.id())
                        .title(platziProductResponse.title())
                        .price(platziProductResponse.price())
                        .quantity(productRequestDTO.quantity())
                        .build());
            });

            savedBasket.setProducts(products);
            savedBasket.calculateTotalPrice();
            return basketRepository.save(savedBasket);
        } else {
            throw new BasketNotFoundException("Basket not found with id: " + basketId);
        }

    }

    public Basket payBasket(String id, PaymentRequest paymentRequest) {
        Optional<Basket> optionalBasket = findBasketById(id);

        if (optionalBasket.isPresent()) {
            Basket savedBasket = optionalBasket.get();
            savedBasket.setPaymentMethod(paymentRequest.paymentMethod());
            savedBasket.setStatus(Status.SOLD);
            return basketRepository.save(savedBasket);

        } else {
            throw new BasketNotFoundException("Basket not found with id: " + id);
        }
    }
}
