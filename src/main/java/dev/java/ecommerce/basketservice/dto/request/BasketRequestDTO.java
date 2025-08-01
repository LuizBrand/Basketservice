package dev.java.ecommerce.basketservice.dto.request;

import java.util.List;

public record BasketRequestDTO(Long clientId, List<ProductRequestDTO> products) {
}
