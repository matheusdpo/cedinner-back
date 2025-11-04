package br.com.matheusdpo.cedinner.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.matheusdpo.cedinner.domain.valueobjects.OrderStatus;

public class OrderResponse {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private List<OrderItemResponse> items;
    private String deliveryAddress;
    private String notes;
    private Double total;

    public OrderResponse() {}

    public OrderResponse(Long id, Long userId, LocalDateTime createdAt, OrderStatus status,
                        List<OrderItemResponse> items, String deliveryAddress, String notes, Double total) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
        this.items = items;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.total = total;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
