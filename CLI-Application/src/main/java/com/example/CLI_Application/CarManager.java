package com.example.CLI_Application;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CarManager {
    private final ApiClient apiClient;
    private final Gson gson = new Gson();

    public CarManager(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Integer createCar(String brand, String model, int year) throws Exception {
        JsonObject carJson = new JsonObject();
        carJson.addProperty("brand", brand);
        carJson.addProperty("model", model);
        carJson.addProperty("year", year);

        String response = apiClient.post("/cars", carJson.toString());
        System.out.println("DEBUG: Response from server: " + response);

        JsonObject responseObj = com.google.gson.JsonParser.parseString(response).getAsJsonObject();
        if (!responseObj.has("id")) {
            throw new RuntimeException("Server response missing 'id' field. Content: " + response);
        }
        return responseObj.get("id").getAsInt();
    }

    public void addFuelEntry(int carId, double liters, double price, int odometer) throws Exception {
        JsonObject fuelJson = new JsonObject();
        fuelJson.addProperty("carId", carId);
        fuelJson.addProperty("liters", liters);
        fuelJson.addProperty("pricePerLiter", price);
        fuelJson.addProperty("odometerReading", odometer);

        apiClient.post("/cars/{id}/fuel", fuelJson.toString());
    }

    public String getFuelStatistics(int carId) throws Exception {
        String response = apiClient.get("/cars/" + carId + "/statistics");

        JsonObject stats = com.google.gson.JsonParser.parseString(response).getAsJsonObject();

        StringBuilder sb = new StringBuilder();
        sb.append("=== Fuel Statistics for Car ID: ").append(carId).append(" ===\n");
        sb.append(String.format("Total fuel consumed: %.2f L\n",
                stats.get("totalFuelLiters").getAsDouble()));
        sb.append(String.format("Total fuel cost: $%.2f\n",
                stats.get("totalFuelCost").getAsDouble()));
        sb.append(String.format("Average consumption: %.1f L/100km\n",
                stats.get("averageConsumption").getAsDouble()));
        sb.append(String.format("Total distance: %d km\n",
                stats.get("totalDistance").getAsInt()));

        return sb.toString();
    }

    public String listCars() throws Exception {
        String response = apiClient.get("/cars");

        JsonArray cars = com.google.gson.JsonParser.parseString(response).getAsJsonArray();

        if (cars.size() == 0) {
            return "No cars found.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Registered Cars ===\n");

        for (int i = 0; i < cars.size(); i++) {
            JsonObject car = cars.get(i).getAsJsonObject();
            sb.append(String.format("ID: %d | %s %s (%d)\n",
                    car.get("id").getAsInt(),
                    car.get("brand").getAsString(),
                    car.get("model").getAsString(),
                    car.get("year").getAsInt()));
        }

        return sb.toString();
    }

    public String getCarInfo(int carId) throws Exception {
        String response = apiClient.get("/cars/" + carId);

        JsonObject car = com.google.gson.JsonParser.parseString(response).getAsJsonObject();

        StringBuilder sb = new StringBuilder();
        sb.append("=== Car Details ===\n");
        sb.append(String.format("ID: %d\n", car.get("id").getAsInt()));
        sb.append(String.format("Brand: %s\n", car.get("brand").getAsString()));
        sb.append(String.format("Model: %s\n", car.get("model").getAsString()));
        sb.append(String.format("Year: %d\n", car.get("year").getAsInt()));

        return sb.toString();
    }
}