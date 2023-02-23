package br.com.ndc.ecommercedemoapi.controller;

import br.com.ndc.ecommercedemoapi.model.Product;
import br.com.ndc.ecommercedemoapi.service.ProductService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public @NotNull Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }
}
