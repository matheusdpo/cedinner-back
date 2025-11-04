package br.com.matheusdpo.cedinner.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.matheusdpo.cedinner.application.dto.OrderItemResponse;
import br.com.matheusdpo.cedinner.application.dto.OrderResponse;
import br.com.matheusdpo.cedinner.application.dto.UpdateOrderRequest;
import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.entities.OrderItem;
import br.com.matheusdpo.cedinner.domain.entities.Product;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;
import br.com.matheusdpo.cedinner.domain.repositories.ProductRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderId;
import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;

@Service
public class UpdateOrderUseCase {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public UpdateOrderUseCase(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse execute(Long orderId, UpdateOrderRequest request) {
        // Find order
        OrderId domainOrderId = new OrderId(orderId);
        Order order = orderRepository.findById(domainOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Check if order can be edited
        if (!order.canBeEdited()) {
            throw new IllegalStateException("Order cannot be edited in current status: " + order.getStatus());
        }

        // Update status if provided
        if (request.getStatus() != null) {
            if (!order.getStatus().canTransitionTo(request.getStatus())) {
                throw new IllegalArgumentException("Invalid status transition from " + order.getStatus() + " to " + request.getStatus());
            }
            order.updateStatus(request.getStatus());
        }

        // Update delivery address if provided
        if (request.getDeliveryAddress() != null && !request.getDeliveryAddress().trim().isEmpty()) {
            order.updateDeliveryAddress(request.getDeliveryAddress());
        }

        // Update notes if provided
        if (request.getNotes() != null) {
            order.updateNotes(request.getNotes());
        }

        // Update items if provided
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            // Clear existing items
            order.getItems().clear();
            
            // Add new items
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
                        product.getName()
                );

                order.addItem(orderItem);
            }
        }

        // Save updated order
        Order updatedOrder = orderRepository.save(order);

        // Convert to response
        return convertToResponse(updatedOrder);
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
