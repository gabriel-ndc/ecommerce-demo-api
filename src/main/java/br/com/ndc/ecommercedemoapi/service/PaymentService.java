package br.com.ndc.ecommercedemoapi.service;

import br.com.ndc.ecommercedemoapi.dto.PaymentRequestDTO;
import br.com.ndc.ecommercedemoapi.dto.PaymentResponseDTO;
import br.com.ndc.ecommercedemoapi.model.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentService {

    @Value("${application.payment-url}")
    private String baseUrl;

    public OrderStatus requestPayment(Long orderId) {
        var paymentRequestDTO = new PaymentRequestDTO(orderId);

        var client = WebClient.create(baseUrl);

        var response = client.post()
                .uri("/api/execute-payment")
                .bodyValue(paymentRequestDTO)
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .block();

        if (response == null) {
            return OrderStatus.FAILURE;
        }
        if (response.status().equalsIgnoreCase("success")) {
            return OrderStatus.SUCCESS;
        }
        return OrderStatus.FAILURE;
    }
}
