package br.com.matheusdpo.cedinner.application.dto;

import br.com.matheusdpo.cedinner.domain.valueobjects.OrderStatus;
import jakarta.validation.Valid;

import java.util.List;

public class UpdateOrderRequest {
    private OrderStatus status;
    private String deliveryAddress;
    private String notes;
    @Valid
    private List<OrderItemRequest> items;

    public UpdateOrderRequest() {}

    public UpdateOrderRequest(OrderStatus status, String deliveryAddress, String notes, List<OrderItemRequest> items) {
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.items = items;
    }

    // Getters and Setters
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
