package dev.java.ecommerce.basketservice.client;

import dev.java.ecommerce.basketservice.dto.response.PlatziProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PlatziStoreCliente", url = "${basket.client.platzi}")
public interface PlatziStoreClient {

    @GetMapping("/products")
    List<PlatziProductResponseDTO> getAllProducts();

    @GetMapping("/products/{productId}")
    PlatziProductResponseDTO getProductById(@PathVariable Long productId);

}
