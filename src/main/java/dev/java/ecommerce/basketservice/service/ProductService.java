package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.dto.response.PlatziProductResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final PlatziStoreClient platziStoreClient;

    public ProductService(PlatziStoreClient platziStoreClient) {
        this.platziStoreClient = platziStoreClient;
    }

    @Cacheable(value = "products")
    public List<PlatziProductResponseDTO> getAllproducts() {
        log.info("Getting all prodructs");
        return platziStoreClient.getAllProducts();
    }

    @Cacheable(value = "product", key = "#productId") //needs to be the same that the atribute
    public PlatziProductResponseDTO getProductById(Long productId) {
        log.info("Getting product with id: {}", productId);
        return platziStoreClient.getProductById(productId);
    }

}
