package br.com.matheusdpo.cedinner.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemRequest {
    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    private String addedItems; // Comma-separated list
    private String removedItems; // Comma-separated list
    private String specialInstructions;

    public OrderItemRequest() {}

    public OrderItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderItemRequest(Long productId, Integer quantity, String addedItems, 
                           String removedItems, String specialInstructions) {
        this.productId = productId;
        this.quantity = quantity;
        this.addedItems = addedItems;
        this.removedItems = removedItems;
        this.specialInstructions = specialInstructions;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(String addedItems) {
        this.addedItems = addedItems;
    }

    public String getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(String removedItems) {
        this.removedItems = removedItems;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }
}
