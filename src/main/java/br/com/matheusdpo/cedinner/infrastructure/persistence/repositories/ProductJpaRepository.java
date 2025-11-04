package br.com.matheusdpo.cedinner.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.ProductJpaEntity;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
}
