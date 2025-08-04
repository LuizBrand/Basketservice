package dev.java.ecommerce.basketservice.controller;

import dev.java.ecommerce.basketservice.dto.request.BasketRequestDTO;
import dev.java.ecommerce.basketservice.entity.Basket;
import dev.java.ecommerce.basketservice.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable String id) {
        return basketService.findBasketById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Basket> createBasket(@RequestBody BasketRequestDTO basketRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(basketService.createBasket(basketRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable String id, @RequestBody BasketRequestDTO basketRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(basketService.updateBasket(id, basketRequest));
    }

}
