package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.services.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1/document-required")
public class DocumentRequiredController {
    @Autowired
    DocumentRequiredService documentRequiredService;


}
