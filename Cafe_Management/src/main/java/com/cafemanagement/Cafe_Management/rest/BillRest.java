package com.cafemanagement.Cafe_Management.rest;

import com.cafemanagement.Cafe_Management.entity.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/bill")
public interface BillRest {
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/generatePdf")
    ResponseEntity<String> generatePdf(@RequestBody(required = true) Map<String, Object> requestMap);

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getBills")
    ResponseEntity<List<Bill>> getBills();

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/getPdf")
    public ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestMap);

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Integer id);
}
