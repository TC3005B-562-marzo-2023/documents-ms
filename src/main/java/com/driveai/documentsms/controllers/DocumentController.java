package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocuments() {
        return ResponseEntity.ok()
                .header("Custom-Header", "foo")
                .body(documentService.findAll());
    }
}
