package br.com.matheusdpo.cedinner.domain.repositories;

import java.util.List;
import java.util.Optional;

import br.com.matheusdpo.cedinner.domain.entities.Product;
import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId productId);
    List<Product> findAll();
    boolean existsById(ProductId productId);
    void delete(Product product);
}
