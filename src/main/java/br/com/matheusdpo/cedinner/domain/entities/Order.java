package br.com.matheusdpo.cedinner.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.matheusdpo.cedinner.domain.valueobjects.OrderId;
import br.com.matheusdpo.cedinner.domain.valueobjects.OrderStatus;
import br.com.matheusdpo.cedinner.domain.valueobjects.UserId;

public class Order {
    private OrderId id;
    private UserId userId;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private List<OrderItem> items;
    private String deliveryAddress;
    private String notes;

    public Order(UserId userId, String deliveryAddress, String notes) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.items = new ArrayList<>();
    }

    public Order(OrderId id, UserId userId, LocalDateTime createdAt, OrderStatus status, 
                 List<OrderItem> items, String deliveryAddress, String notes) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
        this.items = new ArrayList<>(items);
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Order item cannot be null");
        }
        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    public void updateStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }
        this.status = newStatus;
    }

    public void updateDeliveryAddress(String newAddress) {
        if (newAddress == null || newAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Delivery address cannot be null or empty");
        }
        this.deliveryAddress = newAddress;
    }

    public void updateNotes(String newNotes) {
        this.notes = newNotes;
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(OrderItem::calculateSubtotal)
                .sum();
    }

    public boolean canBeEdited() {
        return status == OrderStatus.PENDING;
    }

    public boolean canBeDeleted() {
        return status == OrderStatus.PENDING || status == OrderStatus.CANCELLED;
    }

    // Getters
    public OrderId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getNotes() {
        return notes;
    }

    // Setters for infrastructure layer
    public void setId(OrderId id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setItems(List<OrderItem> items) {
        this.items = new ArrayList<>(items);
    }
}
