package br.com.matheusdpo.cedinner.application.usecases;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.matheusdpo.cedinner.application.dto.CreateOrderRequest;
import br.com.matheusdpo.cedinner.application.dto.OrderItemRequest;
import br.com.matheusdpo.cedinner.domain.entities.Order;
import br.com.matheusdpo.cedinner.domain.entities.Product;
import br.com.matheusdpo.cedinner.domain.entities.User;
import br.com.matheusdpo.cedinner.domain.repositories.OrderRepository;
import br.com.matheusdpo.cedinner.domain.repositories.ProductRepository;
import br.com.matheusdpo.cedinner.domain.repositories.UserRepository;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderId;
import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        createOrderUseCase = new CreateOrderUseCase(orderRepository, userRepository, productRepository);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        Long userId = 1L;
        Long productId = 1L;
        String deliveryAddress = "123 Main St";
        String notes = "Please ring the doorbell";

        User user = new User("John Doe", "johndoe", "john@example.com", "123456789", "123 Main St");
        user.setId(new UserId(userId));

        Product product = new Product("Pizza", "Delicious pizza", 25.99, "Food");
        product.setId(new ProductId(productId));

        OrderItemRequest itemRequest = new OrderItemRequest(productId, 2);
        CreateOrderRequest request = new CreateOrderRequest(userId, deliveryAddress, notes, List.of(itemRequest));

        Order savedOrder = new Order(new UserId(userId), deliveryAddress, notes);
        savedOrder.setId(new OrderId(1L));

        when(userRepository.findById(any(UserId.class))).thenReturn(Optional.of(user));
        when(productRepository.findById(any(ProductId.class))).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // When
        var result = createOrderUseCase.execute(request);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(deliveryAddress, result.getDeliveryAddress());
        assertEquals(notes, result.getNotes());
        assertEquals(1, result.getItems().size());
        assertEquals(51.98, result.getTotal(), 0.01); // 2 * 25.99

        verify(userRepository).findById(new UserId(userId));
        verify(productRepository).findById(new ProductId(productId));
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        Long userId = 999L;
        CreateOrderRequest request = new CreateOrderRequest(userId, "123 Main St", "Notes", List.of());

        when(userRepository.findById(any(UserId.class))).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> createOrderUseCase.execute(request));
        
        assertEquals("User not found with ID: " + userId, exception.getMessage());
        verify(userRepository).findById(new UserId(userId));
        verifyNoInteractions(productRepository, orderRepository);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        Long userId = 1L;
        Long productId = 999L;
        
        User user = new User("John Doe", "johndoe", "john@example.com", "123456789", "123 Main St");
        user.setId(new UserId(userId));

        OrderItemRequest itemRequest = new OrderItemRequest(productId, 1);
        CreateOrderRequest request = new CreateOrderRequest(userId, "123 Main St", "Notes", List.of(itemRequest));

        when(userRepository.findById(any(UserId.class))).thenReturn(Optional.of(user));
        when(productRepository.findById(any(ProductId.class))).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> createOrderUseCase.execute(request));
        
        assertEquals("Product not found with ID: " + productId, exception.getMessage());
        verify(userRepository).findById(new UserId(userId));
        verify(productRepository).findById(new ProductId(productId));
        verifyNoInteractions(orderRepository);
    }
}
