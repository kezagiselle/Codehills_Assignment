package com.example.carManagement.controllers;
import com.example.carManagement.models.Car;
import com.example.carManagement.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Create a new car
    @PostMapping("/cars")
     public Car createCar(@RequestBody Map<String, Object> body) {
        String brand = (String) body.get("brand");
        String model = (String) body.get("model");
        int year = (int) body.get("year");
        return carService.createCar(brand, model, year);
    }

    // List all cars
    @GetMapping("/getAllCars")
        public List<Car> listCars() {
        return carService.getAllCars();
    }

    // Add fuel entry to a car
    @PostMapping("/cars/{Id}/fuel")
      public String addFuel(
            @PathVariable Long id,
            @RequestBody Map<String, Double> body
    ) {
        carService.addFuel(
                id,
                body.get("liters"),
                body.get("price"),
                body.get("odometer")
        );
        return "Fuel added successfully";
    }

    //get stats
    @GetMapping("/cars/{Id}/fuel/stats")
       public Map<String, Double> getStats(@PathVariable Long id) {
        return carService.getFuelStatistics(id);
    }
    

}
