package br.com.matheusdpo.cedinner.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.matheusdpo.cedinner.domain.valueobjects.OrderStatus;
import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;

class OrderTest {

    private Order order;
    private UserId userId;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        userId = new UserId(1L);
        order = new Order(userId, "123 Main St", "Please ring doorbell");
        
        orderItem = new OrderItem(
            new ProductId(1L),
            2,
            25.99,
            "Pizza"
        );
    }

    @Test
    void shouldCreateOrderWithCorrectInitialValues() {
        // Then
        assertEquals(userId, order.getUserId());
        assertEquals("123 Main St", order.getDeliveryAddress());
        assertEquals("Please ring doorbell", order.getNotes());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertTrue(order.getItems().isEmpty());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    void shouldAddItemToOrder() {
        // When
        order.addItem(orderItem);

        // Then
        assertEquals(1, order.getItems().size());
        assertTrue(order.getItems().contains(orderItem));
    }

    @Test
    void shouldCalculateTotalCorrectly() {
        // Given
        OrderItem item2 = new OrderItem(new ProductId(2L), 1, 15.50, "Drink");
        
        // When
        order.addItem(orderItem);
        order.addItem(item2);

        // Then
        double expectedTotal = (2 * 25.99) + (1 * 15.50); // 67.48
        assertEquals(expectedTotal, order.calculateTotal(), 0.01);
    }

    @Test
    void shouldUpdateStatusCorrectly() {
        // When
        order.updateStatus(OrderStatus.CONFIRMED);

        // Then
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void shouldUpdateDeliveryAddress() {
        // When
        order.updateDeliveryAddress("456 Oak Ave");

        // Then
        assertEquals("456 Oak Ave", order.getDeliveryAddress());
    }

    @Test
    void shouldUpdateNotes() {
        // When
        order.updateNotes("New notes");

        // Then
        assertEquals("New notes", order.getNotes());
    }

    @Test
    void shouldBeEditableWhenPending() {
        // Then
        assertTrue(order.canBeEdited());
    }

    @Test
    void shouldNotBeEditableWhenConfirmed() {
        // When
        order.updateStatus(OrderStatus.CONFIRMED);

        // Then
        assertFalse(order.canBeEdited());
    }

    @Test
    void shouldBeDeletableWhenPending() {
        // Then
        assertTrue(order.canBeDeleted());
    }

    @Test
    void shouldBeDeletableWhenCancelled() {
        // When
        order.updateStatus(OrderStatus.CANCELLED);

        // Then
        assertTrue(order.canBeDeleted());
    }

    @Test
    void shouldNotBeDeletableWhenDelivered() {
        // When
        order.updateStatus(OrderStatus.DELIVERED);

        // Then
        assertFalse(order.canBeDeleted());
    }

    @Test
    void shouldThrowExceptionWhenAddingNullItem() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> order.addItem(null));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingStatusToNull() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> order.updateStatus(null));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingDeliveryAddressToNull() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> order.updateDeliveryAddress(null));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingDeliveryAddressToEmpty() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> order.updateDeliveryAddress(""));
    }
}
