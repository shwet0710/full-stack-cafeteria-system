package com.cafemanagement.Cafe_Management.rest;

import com.cafemanagement.Cafe_Management.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/getUsers")
    public ResponseEntity<List<UserWrapper>> getAllUser();
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/update")
    public ResponseEntity<String> updateUser(@RequestBody(required = true) Map<String, String> requestMap);
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken();
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody(required = true) Map<String, String> requestMap);
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody(required = true) Map<String, String> requestMap);
}
