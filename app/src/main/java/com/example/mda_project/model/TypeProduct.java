package com.example.mda_project.model;

public class TypeProduct {
    private int typeProId;
    private String proName;
    private String proImage;

    public TypeProduct() {
    }

    public TypeProduct(int typeProId, String proName, String proImage) {
        this.typeProId = typeProId;
        this.proName = proName;
        this.proImage = proImage;
    }

    public int getTypeProId() {
        return typeProId;
    }

    public void setTypeProId(int typeProId) {
        this.typeProId = typeProId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }
}
