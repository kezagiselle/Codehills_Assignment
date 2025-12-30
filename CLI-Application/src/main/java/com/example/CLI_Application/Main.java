package com.example.CLI_Application;

import java.util.Scanner;


//entry point
public class Main {
     private static final String API_BASE_URL = "http://localhost:8080/api";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ApiClient apiClient = new ApiClient(API_BASE_URL);
    private static final CarManager carManager = new CarManager(apiClient);

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            interactiveMode();
        } else {
            batchMode(args);
        }
    }

    private static void interactiveMode() {
        System.out.println("=== Car Fuel Management CLI ===");
        System.out.println("Type 'help' for commands or 'exit' to quit");

        while (true) {
            System.out.print("\ncar-fuel> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }

            if (input.isEmpty()) {
                continue;
            }

            try {
                processCommand(input);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void batchMode(String[] args) {
        try {
            String command = String.join(" ", args);
            processCommand(command);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void processCommand(String input) {
        CommandParser parser = new CommandParser(input);
        String command = parser.getCommand();

        switch (command.toLowerCase()) {
            case "create-car":
                handleCreateCar(parser);
                break;
            case "add-fuel":
                handleAddFuel(parser);
                break;
            case "fuel-stats":
                handleFuelStats(parser);
                break;
            case "list-cars":
                handleListCars();
                break;
            case "car-info":
                handleCarInfo(parser);
                break;
            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Type 'help' for available commands");
        }
    }

    private static void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("  create-car --brand <brand> --model <model> --year <year>");
        System.out.println("  add-fuel --carId <id> --liters <liters> --price <price> --odometer <odometer>");
        System.out.println("  fuel-stats --carId <id>");
        System.out.println("  list-cars");
        System.out.println("  car-info --carId <id>");
        System.out.println("  help");
        System.out.println("  exit");
    }

    private static void handleCreateCar(CommandParser parser) {
        String brand = parser.getArgument("--brand");
        String model = parser.getArgument("--model");
        int year = Integer.parseInt(parser.getArgument("--year"));

        try {
            Integer carId = carManager.createCar(brand, model, year);
            System.out.println("✓ Car created successfully! ID: " + carId);
        } catch (Exception e) {
            System.err.println("Failed to create car: " + e.getMessage());
        }
    }

    private static void handleAddFuel(CommandParser parser) {
        int carId = Integer.parseInt(parser.getArgument("--carId"));
        double liters = Double.parseDouble(parser.getArgument("--liters"));
        double price = Double.parseDouble(parser.getArgument("--price"));
        int odometer = Integer.parseInt(parser.getArgument("--odometer"));

        try {
            carManager.addFuelEntry(carId, liters, price, odometer);
            System.out.println("✓ Fuel entry added successfully!");
        } catch (Exception e) {
            System.err.println("Failed to add fuel entry: " + e.getMessage());
        }
    }

    private static void handleFuelStats(CommandParser parser) {
        int carId = Integer.parseInt(parser.getArgument("--carId"));

        try {
            String stats = carManager.getFuelStatistics(carId);
            System.out.println(stats);
        } catch (Exception e) {
            System.err.println("Failed to get statistics: " + e.getMessage());
        }
    }

    private static void handleListCars() {
        try {
            String cars = carManager.listCars();
            System.out.println(cars);
        } catch (Exception e) {
            System.err.println("Failed to list cars: " + e.getMessage());
        }
    }

    private static void handleCarInfo(CommandParser parser) {
        int carId = Integer.parseInt(parser.getArgument("--carId"));

        try {
            String info = carManager.getCarInfo(carId);
            System.out.println(info);
        } catch (Exception e) {
            System.err.println("Failed to get car info: " + e.getMessage());
        }
    }
}
