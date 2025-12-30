package com.example.carManagement.services;
import com.example.carManagement.models.Car;
import com.example.carManagement.models.FuelEntry;

import org.springframework.stereotype.Service;

import java.util.*;

//here in this service its where am going to use Lists/Maps instead of databse. (In-Memory Storage)
public class CarService {
    
    private Map<Long, Car> cars = new HashMap<>();
    private long idCounter = 1;


    //create a new car
    public Car createCar(String brand, String model, int year) {
        Car car = new Car(idCounter++, brand, model, year);
        cars.put(car.getId(), car);
        return car;
    }

    //List all cars
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    //Add fuel entry to a car
    public  void addFuel(Long carId, double liters, double price, double odometer){
        Car car = cars.get(carId);
        if(car == null){
            throw new RuntimeException("Car not found");
        }
        car.getFuelEntries().add(new FuelEntry(liters, price, odometer));
    }

    //fuel statistics for a car
    public Map<String, Double> getFuelStatistics(Long carId){
        Car car = cars.get(carId);
        if(car == null){
            throw new RuntimeException("Car not found");
        }

       double totalLiters = 0;
       double totalCost = 0;
       double firstOdo = 0;
       double lastOdo = 0;

       var entries = car.getFuelEntries();
       if(!entries.isEmpty()){
           firstOdo = entries.get(0).getOdometer();
           lastOdo = entries.get(entries.size() -1).getOdometer();
       }

        for (FuelEntry f : entries) {
            totalLiters += f.getLiters();
            totalCost += f.getPrice();
        }

        double distance = lastOdo - firstOdo;
        double avgPer100Km = distance > 0 ? (totalLiters / distance) * 100 : 0;

        Map<String, Double> stats = new HashMap<>();
        stats.put("totalFuel", totalLiters);
        stats.put("totalCost", totalCost);
        stats.put("averagePer100Km", avgPer100Km);

        return stats;
    }
}
