package br.com.matheusdpo.cedinner.domain.entities;

import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;

public class OrderItem {
    private ProductId productId;
    private int quantity;
    private double unitPrice;
    private String productName;
    private String addedItems; // Items added to the product (comma-separated)
    private String removedItems; // Items removed from the product (comma-separated)
    private String specialInstructions; // Special instructions for this item

    public OrderItem(ProductId productId, int quantity, double unitPrice, String productName) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productName = productName;
    }

    public OrderItem(ProductId productId, int quantity, double unitPrice, String productName,
                    String addedItems, String removedItems, String specialInstructions) {
        this(productId, quantity, unitPrice, productName);
        this.addedItems = addedItems;
        this.removedItems = removedItems;
        this.specialInstructions = specialInstructions;
    }

    public double calculateSubtotal() {
        return quantity * unitPrice;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;
    }

    public void updateUnitPrice(double newUnitPrice) {
        if (newUnitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        this.unitPrice = newUnitPrice;
    }

    // Getters
    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getProductName() {
        return productName;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderItem orderItem = (OrderItem) obj;
        return productId.equals(orderItem.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
