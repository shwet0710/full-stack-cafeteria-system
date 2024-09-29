package com.cafemanagement.Cafe_Management.service;

import com.cafemanagement.Cafe_Management.constants.CafeConstants;
import com.cafemanagement.Cafe_Management.dao.ProductDao;
import com.cafemanagement.Cafe_Management.entity.Category;
import com.cafemanagement.Cafe_Management.entity.Product;
import com.cafemanagement.Cafe_Management.jwt.JwtFilter;
import com.cafemanagement.Cafe_Management.utils.CafeUtils;
import com.cafemanagement.Cafe_Management.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return CafeUtils.getMessage("Product Added Successfully", HttpStatus.OK);
                }
            } else {
                return CafeUtils.getMessage(CafeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean flag) {
        Product product = new Product();
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        if (flag) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setstatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        product.setstatus(String.valueOf(flag));
        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean id) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && id) {
                return true;
            } else if (!id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                if(validateProductMap(requestMap, false)) {
                    Optional<Product> optionalProduct = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optionalProduct.isEmpty()) {
                        productDao.save(getProductFromMap(requestMap, true));
                        return CafeUtils.getMessage("Product is updated successfully", HttpStatus.OK);
                    } else {
                        return CafeUtils.getMessage("Product id doesn't exist", HttpStatus.OK);
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

    @Override
    public ResponseEntity<String> delete(Integer id) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional<Product> optionalProduct = productDao.findById(id);
                if(!optionalProduct.isEmpty()) {
                    productDao.deleteById(id);
                    return CafeUtils.getMessage("Deleted successfully", HttpStatus.OK);
                }
                return CafeUtils.getMessage("Product id does not exist", HttpStatus.OK);
            } else {
                return CafeUtils.getMessage(CafeConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getByCategory(id), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Modifying
    @Transactional
    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional optionalProduct = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optionalProduct.isEmpty()) {
                    productDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CafeUtils.getMessage("Status updated successfully", HttpStatus.OK);
                }
                return CafeUtils.getMessage("Product id does not exist", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
