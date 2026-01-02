package com.example.carManagement.services;
import com.example.carManagement.models.Car;
import com.example.carManagement.models.FuelEntry;
import com.example.carManagement.exceptions.ResourceNotFoundException;  

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CarService {
    
    private Map<Long, Car> cars = new HashMap<>();
    private long idCounter = 1;



    public Car createCar(String brand, String model, int year) {
        Car car = new Car(idCounter++, brand, model, year);
        cars.put(car.getId(), car);
        return car;
    }

    
    public List<Car> getAllCars() {
        return new ArrayList<>(cars.values());
    }

    
 public void addFuel(Long carId, Double liters, Double price, Double odometer) {
    
    if (liters == null || price == null || odometer == null) {
        throw new IllegalArgumentException("All fuel parameters must be provided");
    }
    
    Car car = cars.get(carId);
    if (car == null) {
               throw new ResourceNotFoundException("Car with id " + carId + " not found");

    }
    
    car.getFuelEntries().add(new FuelEntry(liters, price, odometer));
}

    
   public Map<String, Double> getFuelStatistics(Long carId) {
    Car car = cars.get(carId);
    if (car == null) {
        return null;
    }

    double totalLiters = 0;
    double totalCost = 0;
    double firstOdo = 0;
    double lastOdo = 0;

    var entries = car.getFuelEntries();
    
    if (!entries.isEmpty()) {
        firstOdo = entries.get(0).getOdometer();
        lastOdo = entries.get(entries.size() - 1).getOdometer();
    }

    for (FuelEntry f : entries) {
        totalLiters += f.getLiters();
        totalCost += f.getLiters() * f.getPrice(); 
    }

    double distance = lastOdo - firstOdo;
    double avgPer100Km = distance > 0 ? (totalLiters / distance) * 100 : 0;
    double costPerKm = distance > 0 ? totalCost / distance : 0;
    double costPerLiter = totalLiters > 0 ? totalCost / totalLiters : 0;

    Map<String, Double> stats = new HashMap<>();
    stats.put("totalFuel", totalLiters);
    stats.put("totalCost", totalCost);
    stats.put("averagePer100Km", avgPer100Km);
    stats.put("costPerKm", costPerKm);
    stats.put("averagePricePerLiter", costPerLiter);
    stats.put("distanceTraveled", distance);

    return stats;
}
}