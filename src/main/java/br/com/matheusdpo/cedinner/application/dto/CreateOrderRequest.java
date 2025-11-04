package br.com.matheusdpo.cedinner.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class CreateOrderRequest {
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotEmpty(message = "Delivery address is required")
    private String deliveryAddress;

    private String notes;

    @NotEmpty(message = "Order items are required")
    @Valid
    private List<OrderItemRequest> items;

    public CreateOrderRequest() {}

    public CreateOrderRequest(Long userId, String deliveryAddress, String notes, List<OrderItemRequest> items) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.items = items;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
