package br.com.matheusdpo.cedinner.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void shouldHaveCorrectDescription() {
        assertEquals("Pending", OrderStatus.PENDING.getDescription());
        assertEquals("Confirmed", OrderStatus.CONFIRMED.getDescription());
        assertEquals("Preparing", OrderStatus.PREPARING.getDescription());
        assertEquals("Ready for Delivery", OrderStatus.READY_FOR_DELIVERY.getDescription());
        assertEquals("Out for Delivery", OrderStatus.OUT_FOR_DELIVERY.getDescription());
        assertEquals("Delivered", OrderStatus.DELIVERED.getDescription());
        assertEquals("Cancelled", OrderStatus.CANCELLED.getDescription());
    }

    @Test
    void shouldIdentifyFinalStatuses() {
        assertTrue(OrderStatus.DELIVERED.isFinalStatus());
        assertTrue(OrderStatus.CANCELLED.isFinalStatus());
        
        assertFalse(OrderStatus.PENDING.isFinalStatus());
        assertFalse(OrderStatus.CONFIRMED.isFinalStatus());
        assertFalse(OrderStatus.PREPARING.isFinalStatus());
        assertFalse(OrderStatus.READY_FOR_DELIVERY.isFinalStatus());
        assertFalse(OrderStatus.OUT_FOR_DELIVERY.isFinalStatus());
    }

    @Test
    void shouldAllowValidStatusTransitions() {
        // PENDING can transition to CONFIRMED or CANCELLED
        assertTrue(OrderStatus.PENDING.canTransitionTo(OrderStatus.CONFIRMED));
        assertTrue(OrderStatus.PENDING.canTransitionTo(OrderStatus.CANCELLED));
        
        // CONFIRMED can transition to PREPARING or CANCELLED
        assertTrue(OrderStatus.CONFIRMED.canTransitionTo(OrderStatus.PREPARING));
        assertTrue(OrderStatus.CONFIRMED.canTransitionTo(OrderStatus.CANCELLED));
        
        // PREPARING can transition to READY_FOR_DELIVERY or CANCELLED
        assertTrue(OrderStatus.PREPARING.canTransitionTo(OrderStatus.READY_FOR_DELIVERY));
        assertTrue(OrderStatus.PREPARING.canTransitionTo(OrderStatus.CANCELLED));
        
        // READY_FOR_DELIVERY can transition to OUT_FOR_DELIVERY or CANCELLED
        assertTrue(OrderStatus.READY_FOR_DELIVERY.canTransitionTo(OrderStatus.OUT_FOR_DELIVERY));
        assertTrue(OrderStatus.READY_FOR_DELIVERY.canTransitionTo(OrderStatus.CANCELLED));
        
        // OUT_FOR_DELIVERY can transition to DELIVERED or CANCELLED
        assertTrue(OrderStatus.OUT_FOR_DELIVERY.canTransitionTo(OrderStatus.DELIVERED));
        assertTrue(OrderStatus.OUT_FOR_DELIVERY.canTransitionTo(OrderStatus.CANCELLED));
    }

    @Test
    void shouldNotAllowInvalidStatusTransitions() {
        // PENDING cannot transition to PREPARING directly
        assertFalse(OrderStatus.PENDING.canTransitionTo(OrderStatus.PREPARING));
        
        // CONFIRMED cannot transition to DELIVERED directly
        assertFalse(OrderStatus.CONFIRMED.canTransitionTo(OrderStatus.DELIVERED));
        
        // DELIVERED cannot transition to any other status
        assertFalse(OrderStatus.DELIVERED.canTransitionTo(OrderStatus.PENDING));
        assertFalse(OrderStatus.DELIVERED.canTransitionTo(OrderStatus.CONFIRMED));
        
        // CANCELLED cannot transition to any other status
        assertFalse(OrderStatus.CANCELLED.canTransitionTo(OrderStatus.PENDING));
        assertFalse(OrderStatus.CANCELLED.canTransitionTo(OrderStatus.CONFIRMED));
    }
}
