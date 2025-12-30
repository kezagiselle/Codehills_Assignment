package com.example.carManagement.servlets;

import com.example.carManagement.services.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.Map;

@Component  
@WebServlet("/servlet/fuel-stats")
public class FuelStatsServlet extends HttpServlet {

    @Autowired
    private CarService carService;

     @Autowired
    public FuelStatsServlet(CarService carService) {
        this.carService = carService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // 1. Set content type
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        // 2. Manually parse carId from query parameters
        String carIdParam = req.getParameter("carId");
        
        // 3. Validate parameter
        if (carIdParam == null || carIdParam.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing carId parameter\"}");
            return;
        }
        
        // 4. Parse and validate numeric format
        Long carId;
        try {
            carId = Long.parseLong(carIdParam);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid carId format\"}");
            return;
        }
        
        try {
            // 5. Use same Service layer as REST API
            Map<String, Double> stats = carService.getFuelStatistics(carId);
            
            // 6. Handle not found or empty data
            if (stats == null || stats.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Car not found or no fuel data\"}");
                return;
            }
            
            // 7. Manually generate JSON response
            String json = generateJson(stats);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);
            
        } catch (RuntimeException e) {
            // Handle service exceptions
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    private String generateJson(Map<String, Double> map) {
        if (map.isEmpty()) return "{}";
        
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (!first) json.append(",");
            first = false;
            
            json.append("\"")
                .append(entry.getKey().replace("\"", "\\\""))
                .append("\":")
                .append(entry.getValue());
        }
        
        return json.append("}").toString();
    }
}