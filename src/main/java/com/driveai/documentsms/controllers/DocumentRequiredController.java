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

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocumentRequired(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        return new ResponseEntity<>(documentRequiredService.findAll(),HttpStatus.OK);
    }

}
