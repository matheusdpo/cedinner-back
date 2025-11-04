package br.com.matheusdpo.cedinner.application.dto;

public class OrderItemResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
    private String addedItems;
    private String removedItems;
    private String specialInstructions;

    public OrderItemResponse() {}

    public OrderItemResponse(Long productId, String productName, Integer quantity, Double unitPrice, Double subtotal) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public OrderItemResponse(Long productId, String productName, Integer quantity, Double unitPrice, 
                           Double subtotal, String addedItems, String removedItems, String specialInstructions) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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
