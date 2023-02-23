package br.com.ndc.ecommercedemoapi.dto;

public record PaymentResponseDTO(
        Long paymentId,
        String status
) {
}
