package com.cafemanagement.Cafe_Management.service;

import com.cafemanagement.Cafe_Management.constants.CafeConstants;
import com.cafemanagement.Cafe_Management.dao.CategoryDao;
import com.cafemanagement.Cafe_Management.entity.Category;
import com.cafemanagement.Cafe_Management.jwt.JwtFilter;
import com.cafemanagement.Cafe_Management.utils.CafeUtils;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                if(validateCategoryMap(requestMap, false)) {
                    categoryDao.save(getCategoryFromMap(requestMap, true));
                    return CafeUtils.getMessage("Added successfully", HttpStatus.OK);
                }
            } else {
                return CafeUtils.getMessage(CafeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String value) {
        try {
            if(!Strings.isNullOrEmpty(value) && value.equalsIgnoreCase("true")) {
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap , true)) {
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        categoryDao.save(getCategoryFromMap(requestMap,true));
                        return CafeUtils.getMessage("Category is updated successfully", HttpStatus.OK);
                    } else {
                        return CafeUtils.getMessage("Category id doesn't exist", HttpStatus.OK);
                    }
                }
                return CafeUtils.getMessage("Invalid data", HttpStatus.BAD_REQUEST);
            } else {
                return CafeUtils.getMessage(CafeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }
    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }
}
