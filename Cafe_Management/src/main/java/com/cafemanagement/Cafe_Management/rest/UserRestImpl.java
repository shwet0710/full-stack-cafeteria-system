package com.cafemanagement.Cafe_Management.rest;

import com.cafemanagement.Cafe_Management.constants.CafeConstants;
import com.cafemanagement.Cafe_Management.service.UserService;
import com.cafemanagement.Cafe_Management.utils.CafeUtils;
import com.cafemanagement.Cafe_Management.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService service;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return service.signup(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return service.login(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return service.getAllUser();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
            return service.updateUser(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return service.checkToken();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            return service.changePassword(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            return service.forgotPassword(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
