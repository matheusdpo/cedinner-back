package br.com.matheusdpo.cedinner.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.matheusdpo.cedinner.application.dto.OrderItemResponse;
import br.com.matheusdpo.cedinner.application.dto.OrderResponse;
import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;
import br.com.matheusdpo.cedinner.domain.repositories.UserRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;

@Service
public class GetOrdersByUserUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public GetOrdersByUserUseCase(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<OrderResponse> execute(Long userId) {
        // Validate user exists
        UserId userDomainId = new UserId(userId);
        if (!userRepository.existsById(userDomainId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // Get orders by user
        List<Order> orders = orderRepository.findByUserId(userDomainId);

        // Convert to response
        return orders.stream()
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
