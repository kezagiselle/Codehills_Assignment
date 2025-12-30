package com.example.carManagement.controllers;
import com.example.carManagement.models.Car;
import com.example.carManagement.services.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Create a new car
    @PostMapping(value = "/cars", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
     public Car createCar(@RequestBody Map<String, Object> body) {
        String brand = (String) body.get("brand");
        String model = (String) body.get("model");
        int year = (int) body.get("year");
        return carService.createCar(brand, model, year);
    }

    // List all cars
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> listCars() {

    List<Car> cars = carService.getAllCars();

    if (cars == null || cars.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cars);
}

    // Add fuel entry to a car
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value ="/cars/{id}/fuel", consumes = "application/json")
   public ResponseEntity<?> addFuel(
    @PathVariable("id") Long id,  
    @RequestBody Map<String, Double> body
) {
    
    if (!body.containsKey("liters") || !body.containsKey("price") || !body.containsKey("odometer")) {
        return ResponseEntity.badRequest()
            .body("Missing required fields: liters, price, and odometer are all required");
    }
    
    if (body.get("liters") == null || body.get("price") == null || body.get("odometer") == null) {
        return ResponseEntity.badRequest()
            .body("Fields cannot be null: liters, price, and odometer must have numeric values");
    }
    
    carService.addFuel(
        id,
        body.get("liters"),
        body.get("price"),
        body.get("odometer")
    );
    return ResponseEntity.ok("Fuel added successfully");
}

    //get stats
   @GetMapping("/cars/{id}/fuel/stats")
  public ResponseEntity<Map<String, Double>> getStats(@PathVariable("id") Long id) {
    
    Map<String, Double> stats = carService.getFuelStatistics(id);
    
    if (stats == null || stats.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)  
                .build();
    }
    
    return ResponseEntity
            .status(HttpStatus.OK)  
            .body(stats);
}
    

}
