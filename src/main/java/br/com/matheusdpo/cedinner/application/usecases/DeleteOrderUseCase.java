package br.com.matheusdpo.cedinner.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderId;

@Service
public class DeleteOrderUseCase {
    private final OrderRepository orderRepository;

    public DeleteOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void execute(Long orderId) {
        // Find order
        OrderId domainOrderId = new OrderId(orderId);
        Order order = orderRepository.findById(domainOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Check if order can be deleted
        if (!order.canBeDeleted()) {
            throw new IllegalStateException("Order cannot be deleted in current status: " + order.getStatus());
        }

        // Delete order
        orderRepository.delete(order);
    }
}
