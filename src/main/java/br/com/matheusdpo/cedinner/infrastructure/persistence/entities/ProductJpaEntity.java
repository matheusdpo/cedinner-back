package br.com.matheusdpo.cedinner.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class ProductJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "addable_items", columnDefinition = "TEXT")
    private String addableItems;

    @Column(name = "removable_items", columnDefinition = "TEXT")
    private String removableItems;

    public ProductJpaEntity() {}

    public ProductJpaEntity(String name, String description, Double price, Boolean available, String category,
                           String imageUrl, String addableItems, String removableItems) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.category = category;
        this.imageUrl = imageUrl;
        this.addableItems = addableItems;
        this.removableItems = removableItems;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddableItems() {
        return addableItems;
    }

    public void setAddableItems(String addableItems) {
        this.addableItems = addableItems;
    }

    public String getRemovableItems() {
        return removableItems;
    }

    public void setRemovableItems(String removableItems) {
        this.removableItems = removableItems;
    }
}
