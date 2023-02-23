package br.com.ndc.ecommercedemoapi.controller;

import br.com.ndc.ecommercedemoapi.dto.OrderProductDTO;
import br.com.ndc.ecommercedemoapi.exception.ResourceNotFoundException;
import br.com.ndc.ecommercedemoapi.model.Order;
import br.com.ndc.ecommercedemoapi.model.OrderProduct;
import br.com.ndc.ecommercedemoapi.model.OrderStatus;
import br.com.ndc.ecommercedemoapi.service.OrderProductService;
import br.com.ndc.ecommercedemoapi.service.OrderService;
import br.com.ndc.ecommercedemoapi.service.PaymentService;
import br.com.ndc.ecommercedemoapi.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;
    private final OrderProductService orderProductService;
    private final PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody OrderForm form) {
        var formDTOs = form.getProductOrders();
        validateProductExistence(formDTOs);

        var order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order = this.orderService.create(order);

        var orderProducts = new ArrayList<OrderProduct>();
        for (var dto : formDTOs) {
            var newOrderProduct = orderProductService.create(
                    new OrderProduct(
                            order,
                            productService.getProduct(dto.getProduct().getId()),
                            dto.getQuantity()
                    )
            );
            orderProducts.add(newOrderProduct);
        }

        order.setOrderProducts(orderProducts);

        this.orderService.update(order);

        try {
            var paymentStatus = this.paymentService.requestPayment(order.getId());
            order.setStatus(paymentStatus);
            this.orderService.update(order);
        } catch (Exception e) {
            var headers = new HttpHeaders();
            return new ResponseEntity("Could not execute payment", headers, HttpStatus.BAD_GATEWAY);
        }

        var uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/orders/{id}")
                .buildAndExpand(order.getId())
                .toString();
        var headers = new HttpHeaders();
        headers.add("Location", uri);

        var httpStatus = HttpStatus.CREATED;
        if (order.getStatus().equals(OrderStatus.FAILURE)) {
            httpStatus = HttpStatus.BAD_GATEWAY;
        }

        return new ResponseEntity(order, headers, httpStatus);
    }

    private void validateProductExistence(List<OrderProductDTO> orderProducts) {
        var list = orderProducts.stream()
                .filter(op -> Objects.isNull(
                        productService.getProduct(op.getProduct().getId())
                ))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    @Getter
    @Setter
    public static class OrderForm {
        private List<OrderProductDTO> productOrders;
    }
}
