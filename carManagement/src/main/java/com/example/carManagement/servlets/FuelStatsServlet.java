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
        
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        
        String carIdParam = req.getParameter("carId");
        
        
        if (carIdParam == null || carIdParam.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing carId parameter\"}");
            return;
        }
        
        
        Long carId;
        try {
            carId = Long.parseLong(carIdParam);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid carId format\"}");
            return;
        }
        
        try {

            Map<String, Double> stats = carService.getFuelStatistics(carId);
            
            
            if (stats == null || stats.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Car not found or no fuel data\"}");
                return;
            }
            
            
            String json = generateJson(stats);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);
            
        } catch (RuntimeException e) {
            
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