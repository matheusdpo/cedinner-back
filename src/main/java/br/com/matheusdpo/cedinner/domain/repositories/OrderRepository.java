package br.com.matheusdpo.cedinner.domain.repositories;

import java.util.List;
import java.util.Optional;

import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderId;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId orderId);
    List<Order> findByUserId(UserId userId);
    List<Order> findAll();
    void delete(Order order);
    boolean existsById(OrderId orderId);
}
