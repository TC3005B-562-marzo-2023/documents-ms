package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.services.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/document-required")
public class DocumentRequiredController {
    @Autowired
    DocumentRequiredService documentRequiredService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocumentRequired(Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        return new ResponseEntity<>(documentRequiredService.findAll(),HttpStatus.OK);
    }


}
