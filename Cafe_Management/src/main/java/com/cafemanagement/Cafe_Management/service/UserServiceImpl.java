package com.cafemanagement.Cafe_Management.service;

import com.cafemanagement.Cafe_Management.constants.CafeConstants;
import com.cafemanagement.Cafe_Management.dao.UserDao;
import com.cafemanagement.Cafe_Management.entity.User;
import com.cafemanagement.Cafe_Management.jwt.CustomerUserDetailsService;
import com.cafemanagement.Cafe_Management.jwt.JwtFilter;
import com.cafemanagement.Cafe_Management.jwt.jwtUtil;
import com.cafemanagement.Cafe_Management.utils.CafeUtils;
import com.cafemanagement.Cafe_Management.utils.EmailUtils;
import com.cafemanagement.Cafe_Management.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    jwtUtil jwtUtil;

    @Autowired
    EmailUtils emailUtil;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        log.info("Inside signup", requestMap);
        try {
            if(validateSignUp(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if(Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getMessage(CafeConstants.SUCCESS, HttpStatus.CREATED);
                } else {
                    return CafeUtils.getMessage("Email Already Exist", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getMessage(CafeConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUp(Map<String, String> requestMap) {
        if(requestMap.containsKey("name") && requestMap.containsKey("email") && requestMap.containsKey("password") && requestMap.containsKey("contactNumber")) {
            return true;
        }
        return false;
    }
    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus(requestMap.get("status"));
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login method", requestMap);
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(
                            customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for Admin Approvel." + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if(jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional<User> optionalUser = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optionalUser.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CafeUtils.getMessage(CafeConstants.UPDATESUCCESS, HttpStatus.OK);
                } else {
                    return CafeUtils.getMessage(CafeConstants.UNAUTHORIZED, HttpStatus.OK);
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
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getMessage("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(jwtFilter.getCurrentUsername());
            if(!user.equals(null)) {
                if(user.getPassword().equals(requestMap.get("oldPassword"))) {
                    user.setPassword(requestMap.get("newPassword"));
                    userDao.save(user);
                    return CafeUtils.getMessage(CafeConstants.UPDATESUCCESS, HttpStatus.OK);
                } else {
                    return CafeUtils.getMessage("Incorrect old password", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
                emailUtil.forgetMail(user.getEmail() , "Credentials by Cafe Management System" , user.getPassword());
                return CafeUtils.getMessage("Check Your mail for Credentials", HttpStatus.OK);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getMessage(CafeConstants.WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
