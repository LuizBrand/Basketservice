package dev.java.ecommerce.basketservice.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public record PlatziProductResponseDTO(Long id, String title, BigDecimal price) implements Serializable {
}
