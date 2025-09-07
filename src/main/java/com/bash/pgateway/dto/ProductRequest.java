package com.bash.pgateway.dto;

public record ProductRequest(
        Long amount,
        Long quantity,
        String name,
        String currency
) {
}
