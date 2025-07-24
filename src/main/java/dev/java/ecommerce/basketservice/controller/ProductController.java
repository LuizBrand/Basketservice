package dev.java.ecommerce.basketservice.controller;

import dev.java.ecommerce.basketservice.dto.response.PlatziProductResponseDTO;
import dev.java.ecommerce.basketservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<PlatziProductResponseDTO>> getAllProdcuts() {
        return ResponseEntity.ok().body(productService.getAllproducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatziProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

}
