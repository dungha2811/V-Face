package com.example.vface.java.entity;

public class UserFaceVector {
    private String Id;
    private double percentage;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public UserFaceVector(String id, double percentage) {
        Id = id;
        this.percentage = percentage;
    }
}
