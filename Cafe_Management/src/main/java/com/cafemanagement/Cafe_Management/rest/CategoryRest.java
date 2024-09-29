package com.cafemanagement.Cafe_Management.rest;

import com.cafemanagement.Cafe_Management.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
public interface CategoryRest {
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/addCategory")
    public ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String, String> requestMap);

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/get")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String Value);

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

}
