package br.com.matheusdpo.cedinner.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.matheusdpo.cedinner.domain.entities.Product;
import br.com.matheusdpo.cedinner.domain.repositories.ProductRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.ProductJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.repositories.ProductJpaRepository;

@Component
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductJpaEntity jpaEntity = toJpaEntity(product);
        ProductJpaEntity savedEntity = productJpaRepository.save(jpaEntity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return productJpaRepository.findById(productId.getValue())
                .map(this::toDomainEntity);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
                .stream()
                .map(this::toDomainEntity)
                .toList();
    }

    @Override
    public boolean existsById(ProductId productId) {
        return productJpaRepository.existsById(productId.getValue());
    }

    @Override
    public void delete(Product product) {
        ProductJpaEntity jpaEntity = toJpaEntity(product);
        productJpaRepository.delete(jpaEntity);
    }

    private ProductJpaEntity toJpaEntity(Product product) {
        ProductJpaEntity entity = new ProductJpaEntity();
        if (product.getId() != null) {
            entity.setId(product.getId().getValue());
        }
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setAvailable(product.isAvailable());
        entity.setCategory(product.getCategory());
        entity.setImageUrl(product.getImageUrl());
        entity.setAddableItems(product.getAddableItems());
        entity.setRemovableItems(product.getRemovableItems());
        return entity;
    }

    private Product toDomainEntity(ProductJpaEntity entity) {
        Product product = new Product(
                new ProductId(entity.getId()),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getAvailable(),
                entity.getCategory(),
                entity.getImageUrl(),
                entity.getAddableItems(),
                entity.getRemovableItems()
        );
        return product;
    }
}
