package dev.java.ecommerce.basketservice.service;

import dev.java.ecommerce.basketservice.client.PlatziStoreClient;
import dev.java.ecommerce.basketservice.dto.response.PlatziProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final PlatziStoreClient platziStoreClient;

    public ProductService(PlatziStoreClient platziStoreClient) {
        this.platziStoreClient = platziStoreClient;
    }

    public List<PlatziProductResponseDTO> getAllproducts() {
        return platziStoreClient.getAllProducts();
    }

    public PlatziProductResponseDTO getProductById(Long id) {
        return platziStoreClient.getProductById(id);
    }

}
