package br.com.ndc.ecommercedemoapi.dto;

import br.com.ndc.ecommercedemoapi.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductDTO {

    private Product product;
    private Integer quantity;
}
