package dev.java.ecommerce.basketservice.dto.request;

import dev.java.ecommerce.basketservice.entity.PaymentMethod;

public record PaymentRequest(PaymentMethod paymentMethod) {
}
