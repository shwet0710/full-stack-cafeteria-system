package com.cafemanagement.Cafe_Management.rest;

import com.cafemanagement.Cafe_Management.constants.CafeConstants;
import com.cafemanagement.Cafe_Management.entity.Category;
import com.cafemanagement.Cafe_Management.service.CategoryService;
import com.cafemanagement.Cafe_Management.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService service;
    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            return service.addNewCategory(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String value) {
        try {
            return service.getAllCategory(value);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return service.update(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
