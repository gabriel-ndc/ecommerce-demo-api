package br.com.ndc.ecommercedemoapi.service;

import br.com.ndc.ecommercedemoapi.model.OrderProduct;
import br.com.ndc.ecommercedemoapi.repository.OrderProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    public OrderProduct create(OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }
}
