package com.cafemanagement.Cafe_Management.rest;

import com.cafemanagement.Cafe_Management.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardRestImpl implements DashboardRest {

    @Autowired
    private DashboardService service;
    @Override
    public ResponseEntity<Map<String, Object>> count() {
        return service.getCount();
    }
}
