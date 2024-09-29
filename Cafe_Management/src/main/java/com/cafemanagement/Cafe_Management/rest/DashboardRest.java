package com.cafemanagement.Cafe_Management.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/dashboard")
public interface DashboardRest {
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> count();
}
