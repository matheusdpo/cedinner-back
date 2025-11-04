package br.com.matheusdpo.cedinner.application.usecases;

import br.com.matheusdpo.cedinner.application.dto.CreateOrderRequest;
import br.com.matheusdpo.cedinner.application.dto.OrderResponse;
import br.com.matheusdpo.cedinner.application.dto.OrderItemResponse;
import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.entities.OrderItem;
import br.com.matheusdpo.cedinner.domain.entities.Product;
import br.com.matheusdpo.cedinner.domain.entities.User;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;
import br.com.matheusdpo.cedinner.domain.repositories.ProductRepository;
import br.com.matheusdpo.cedinner.domain.repositories.UserRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CreateOrderUseCase(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse execute(CreateOrderRequest request) {
        // Validate user exists
        UserId userId = new UserId(request.getUserId());
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        // Create order
        Order order = new Order(userId, request.getDeliveryAddress(), request.getNotes());

        // Add items to order
        for (var itemRequest : request.getItems()) {
            ProductId productId = new ProductId(itemRequest.getProductId());
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + itemRequest.getProductId()));

            if (!product.isAvailable()) {
                throw new IllegalArgumentException("Product is not available: " + product.getName());
            }

            OrderItem orderItem = new OrderItem(
                    productId,
                    itemRequest.getQuantity(),
                    product.getPrice(),
                    product.getName(),
                    itemRequest.getAddedItems(),
                    itemRequest.getRemovedItems(),
                    itemRequest.getSpecialInstructions()
            );

            order.addItem(orderItem);
        }

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Convert to response
        return convertToResponse(savedOrder);
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
