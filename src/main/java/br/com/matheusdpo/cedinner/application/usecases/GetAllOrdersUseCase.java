package br.com.matheusdpo.cedinner.application.usecases;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.matheusdpo.cedinner.application.dto.OrderItemResponse;
import br.com.matheusdpo.cedinner.application.dto.OrderResponse;
import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;

@Service
public class GetAllOrdersUseCase {
    private final OrderRepository orderRepository;

    public GetAllOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponse> execute() {
        // Get all orders and sort by creation date (oldest first)
        List<Order> orders = orderRepository.findAll();
        
        return orders.stream()
                .sorted(Comparator.comparing(Order::getCreatedAt))
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId().getValue(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.calculateSubtotal(),
                        item.getAddedItems(),
                        item.getRemovedItems(),
                        item.getSpecialInstructions()
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

