package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/log")
public class LogController {
    @Autowired
    LogService logService;


    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findByIdLog(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(logService.findById(id),HttpStatus.OK);

        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", "DocumentRequired could not be updated: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocumentRequired() {
        //JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        return new ResponseEntity<>(logService.findAll(),HttpStatus.OK);
    }
}
