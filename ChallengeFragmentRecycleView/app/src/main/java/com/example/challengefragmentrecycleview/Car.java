package com.example.challengefragmentrecycleview;

public class Car {
    private String make;
    private String model;
    private String ownerName;
    private String phoneNumber;

    public Car(String make, String model, String ownerName, String phoneNumber) {
        this.make = make;
        this.model = model;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
