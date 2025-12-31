package com.example.CLI_Application;

import java.util.*;

public class CommandParser { 
    private final String command;
    private final Map<String, String> arguments;

    // constructor
    public CommandParser(String input) {
        String[] parts = input.split("\\s+");
        
        if (parts.length == 0) {
            this.command = "";
            this.arguments = new HashMap<>();
            return;
        }
        
        this.command = parts[0];
        this.arguments = parseArguments(parts);
    }

    private Map<String, String> parseArguments(String[] parts) {
        Map<String, String> args = new HashMap<>();
        
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("--")) {
                String key = parts[i];
                String value = "";
                
                if (i + 1 < parts.length && !parts[i + 1].startsWith("--")) {
                    value = parts[i + 1];
                    i++; // Skip the value in next iteration
                }
                
                args.put(key, value);
            }
        }
        
        return args;
    }

    public String getCommand() {
        return command;
    }

    public String getArgument(String key) {
        String value = arguments.get(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Missing required argument: " + key);
        }
        return value;
    }

    public String getArgument(String key, String defaultValue) {
        String value = arguments.get(key);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    public boolean hasArgument(String key) {
        return arguments.containsKey(key);
    }
}