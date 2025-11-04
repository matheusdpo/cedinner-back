package br.com.matheusdpo.cedinner.infrastructure.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.OrderJpaEntity;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
    List<OrderJpaEntity> findByUserId(Long userId);
}
