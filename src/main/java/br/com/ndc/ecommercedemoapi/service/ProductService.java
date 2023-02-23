package br.com.ndc.ecommercedemoapi.service;

import br.com.ndc.ecommercedemoapi.exception.ResourceNotFoundException;
import br.com.ndc.ecommercedemoapi.model.Product;
import br.com.ndc.ecommercedemoapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
