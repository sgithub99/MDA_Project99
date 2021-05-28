package com.example.mda_project.model;

public class Cart {
    private int proId;
    private String proName;
    private long price;
    private String proImage;
    private int proNumber;


    public Cart(int proId, String proName, long price, String proImage, int proNumber) {
        this.proId = proId;
        this.proName = proName;
        this.price = price;
        this.proImage = proImage;
        this.proNumber = proNumber;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public int getProNumber() {
        return proNumber;
    }

    public void setProNumber(int proNumber) {
        this.proNumber = proNumber;
    }
}
