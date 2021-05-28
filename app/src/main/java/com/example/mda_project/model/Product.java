package com.example.mda_project.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int proId;
    private String proName;
    private Integer price;
    private String proImage;
    private String description;
    private int typeProId;

    public Product() {
    }

    public Product(int proId, String proName, Integer price, String proImage, String description, int typeProId) {
        this.proId = proId;
        this.proName = proName;
        this.price = price;
        this.proImage = proImage;
        this.description = description;
        this.typeProId = typeProId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTypeProId() {
        return typeProId;
    }

    public void setTypeProId(int typeProId) {
        this.typeProId = typeProId;
    }
}
