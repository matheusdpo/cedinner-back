package br.com.matheusdpo.cedinner.infrastructure.persistence.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.entities.OrderItem;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderId;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderStatus;
import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.OrderItemJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.OrderJpaEntity;
import br.com.matheusdpo.cedinner.infrastructure.persistence.entities.OrderStatusJpa;
import br.com.matheusdpo.cedinner.infrastructure.persistence.repositories.OrderJpaRepository;

@Component
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryAdapter(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity jpaEntity = toJpaEntity(order);
        OrderJpaEntity savedEntity = orderJpaRepository.save(jpaEntity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue())
                .map(this::toDomainEntity);
    }

    @Override
    public List<Order> findByUserId(UserId userId) {
        return orderJpaRepository.findByUserId(userId.getValue())
                .stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll()
                .stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Order order) {
        OrderJpaEntity jpaEntity = toJpaEntity(order);
        orderJpaRepository.delete(jpaEntity);
    }

    @Override
    public boolean existsById(OrderId orderId) {
        return orderJpaRepository.existsById(orderId.getValue());
    }

    private OrderJpaEntity toJpaEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        if (order.getId() != null) {
            entity.setId(order.getId().getValue());
        }
        entity.setUserId(order.getUserId().getValue());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setStatus(mapToJpaStatus(order.getStatus()));
        entity.setDeliveryAddress(order.getDeliveryAddress());
        entity.setNotes(order.getNotes());

        List<OrderItemJpaEntity> itemEntities = order.getItems().stream()
                .map(item -> {
                    OrderItemJpaEntity itemEntity = new OrderItemJpaEntity();
                    itemEntity.setProductId(item.getProductId().getValue());
                    itemEntity.setProductName(item.getProductName());
                    itemEntity.setQuantity(item.getQuantity());
                    itemEntity.setUnitPrice(item.getUnitPrice());
                    itemEntity.setAddedItems(item.getAddedItems());
                    itemEntity.setRemovedItems(item.getRemovedItems());
                    itemEntity.setSpecialInstructions(item.getSpecialInstructions());
                    itemEntity.setOrder(entity);
                    return itemEntity;
                })
                .collect(Collectors.toList());

        entity.setItems(itemEntities);
        return entity;
    }

    private Order toDomainEntity(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(item -> new OrderItem(
                        new ProductId(item.getProductId()),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getProductName(),
                        item.getAddedItems(),
                        item.getRemovedItems(),
                        item.getSpecialInstructions()
                ))
                .collect(Collectors.toList());

        Order order = new Order(
                new UserId(entity.getUserId()),
                entity.getDeliveryAddress(),
                entity.getNotes()
        );

        order.setId(new OrderId(entity.getId()));
        order.setCreatedAt(entity.getCreatedAt());
        order.setStatus(mapToDomainStatus(entity.getStatus()));
        order.setItems(items);

        return order;
    }

    private OrderStatusJpa mapToJpaStatus(OrderStatus domainStatus) {
        return OrderStatusJpa.valueOf(domainStatus.name());
    }

    private OrderStatus mapToDomainStatus(OrderStatusJpa jpaStatus) {
        return OrderStatus.valueOf(jpaStatus.name());
    }
}
