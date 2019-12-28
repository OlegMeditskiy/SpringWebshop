package com.example.sfff.domain;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Product() {
    }

    public Product(String title, String description, User user) {
        this.author = user;
        this.description = description;
        this.title = title;
    }

    public String getAuthorName(){
        return author != null?author.getUsername():"<none>";
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setId(Integer id) {
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


}
