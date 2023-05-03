package com.driveai.documentsms.controllers;

import com.driveai.documentsms.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @PostMapping("/get-upload-url")
    public ResponseEntity<HashMap<String, String>> getUploadURL() {
        String fileName = String.valueOf(UUID.randomUUID());
        HashMap<String, String> message = new HashMap<>();
        message.put("filename", fileName);
        String uploadURL = documentService.createUploadURL(fileName);
        message.put("uploadURL", uploadURL);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocuments() {
        return ResponseEntity.ok()
                .header("Custom-Header", "foo")
                .body(documentService.findAll());
    }
}
