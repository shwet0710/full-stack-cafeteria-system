package com.cafemanagement.Cafe_Management.dao;

import com.cafemanagement.Cafe_Management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    List<Category> getAllCategory();
}
