package com.cafemanagement.Cafe_Management.dao;

import com.cafemanagement.Cafe_Management.entity.Product;
import com.cafemanagement.Cafe_Management.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProduct();

    List<ProductWrapper> getByCategory(@Param("id") Integer id);

    ProductWrapper getProductById(@Param("id") Integer id);

    @Modifying
    @Transactional
    void updateStatus(@Param("status") String status, @Param("id") Integer id);

}
