package com.usst.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Item {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer userId;
    private LocalDateTime createdAt;
    private String username; // For displaying the owner's username

    public Item() {}

    public Item(String name, String description, BigDecimal price, Integer userId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}