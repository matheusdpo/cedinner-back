package br.com.matheusdpo.cedinner.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.matheusdpo.cedinner.application.dto.OrderItemResponse;
import br.com.matheusdpo.cedinner.application.dto.OrderResponse;
import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderId;

@Service
public class GetOrderByIdUseCase {
    private final OrderRepository orderRepository;

    public GetOrderByIdUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse execute(Long orderId) {
        // Find order
        OrderId domainOrderId = new OrderId(orderId);
        Order order = orderRepository.findById(domainOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Convert to response
        return convertToResponse(order);
    }

    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId().getValue(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.calculateSubtotal()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId().getValue(),
                order.getUserId().getValue(),
                order.getCreatedAt(),
                order.getStatus(),
                itemResponses,
                order.getDeliveryAddress(),
                order.getNotes(),
                order.calculateTotal()
        );
    }
}
