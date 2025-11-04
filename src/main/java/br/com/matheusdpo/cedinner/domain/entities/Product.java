package br.com.matheusdpo.cedinner.domain.entities;

import br.com.matheusdpo.cedinner.domain.valueobjects.ProductId;

public class Product {
    private ProductId id;
    private String name;
    private String description;
    private double price;
    private boolean available;
    private String category;
    private String imageUrl;
    private String addableItems; // Comma-separated list of items that can be added
    private String removableItems; // Comma-separated list of items that can be removed

    public Product(String name, String description, double price, String category) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }

        this.name = name;
        this.description = description;
        this.price = price;
        this.available = true;
        this.category = category;
    }

    public Product(ProductId id, String name, String description, double price, boolean available, 
                   String category, String imageUrl, String addableItems, String removableItems) {
        this(name, description, price, category);
        this.id = id;
        this.available = available;
        this.imageUrl = imageUrl;
        this.addableItems = addableItems;
        this.removableItems = removableItems;
    }

    public void updatePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        this.price = newPrice;
    }

    public void updateAvailability(boolean available) {
        this.available = available;
    }

    public void updateDetails(String name, String description, String category) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        this.description = description;
        this.category = category;
    }

    // Getters
    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAddableItems() {
        return addableItems;
    }

    public String getRemovableItems() {
        return removableItems;
    }

    // Setters for infrastructure layer
    public void setId(ProductId id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAddableItems(String addableItems) {
        this.addableItems = addableItems;
    }

    public void setRemovableItems(String removableItems) {
        this.removableItems = removableItems;
    }
}
