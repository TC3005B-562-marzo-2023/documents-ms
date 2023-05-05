package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.services.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/document-required")
public class DocumentRequiredController {
    @Autowired
    DocumentRequiredService documentRequiredService;

    @PostMapping("/create")
    public ResponseEntity<?> createRequiredDocument(@RequestBody DocumentRequired documentRequired) {
        try {
            documentRequired = documentRequiredService.saveRequiredDocument(documentRequired);
        } catch (Error e) {
            Map<String,String> response= new HashMap<>();
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(documentRequired, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDocumentRequired (@PathVariable("id") int id, @RequestBody DocumentRequired documentRequired) {
        try {
            DocumentRequired newDoc = documentRequiredService.updateDocumentRequiredById(id, documentRequired);
            return new ResponseEntity<>(newDoc, HttpStatus.OK);
        }
        catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", "DocumentRequired could not be updated: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findByIdDocumentRequired(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(documentRequiredService.findById(id),HttpStatus.OK);

        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", "DocumentRequired could not be updated: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocumentRequired() {
        //JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        return new ResponseEntity<>(documentRequiredService.findAll(),HttpStatus.OK);
    }

}
