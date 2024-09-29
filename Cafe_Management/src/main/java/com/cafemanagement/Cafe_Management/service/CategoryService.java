package com.cafemanagement.Cafe_Management.service;

import com.cafemanagement.Cafe_Management.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAllCategory(String value);

    ResponseEntity<String> update(Map<String, String> requestMap);
}
