package br.com.ndc.ecommercedemoapi.repository;

import br.com.ndc.ecommercedemoapi.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
