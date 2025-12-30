package com.example.carManagement.models;


public class FuelEntry {
    private double liters;
    private double price;
    private double odometer;

    public FuelEntry(double liters, double price, double odometer) {
        this.liters = liters;
        this.price = price;
        this.odometer = odometer;
    }
    
    public double getLiters(){ return liters;}
    public double getPrice(){ return price;}
    public double getOdometer(){ return odometer;}
}

