package com.example.sfff.domain;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public Product() {
    }

    public Product(Category category, String title, String description, int price) {
        this.description = description;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String text) {
        this.description = text;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String tag) {
        this.title = tag;
    }

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Enumerated(EnumType.ORDINAL)
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
