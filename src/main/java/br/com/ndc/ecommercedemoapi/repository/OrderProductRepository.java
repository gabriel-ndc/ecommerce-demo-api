package br.com.ndc.ecommercedemoapi.repository;

import br.com.ndc.ecommercedemoapi.model.OrderProduct;
import br.com.ndc.ecommercedemoapi.model.OrderProductPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
