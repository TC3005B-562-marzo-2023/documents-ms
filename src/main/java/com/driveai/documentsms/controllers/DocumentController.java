package com.driveai.documentsms.controllers;

import com.amazonaws.HttpMethod;
import com.driveai.documentsms.dto.DocumentUploadDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping("/get-upload-url")
    public ResponseEntity<HashMap<String, String>> getUploadURL() {
        String fileName = String.valueOf(UUID.randomUUID());
        HashMap<String, String> message = new HashMap<>();
        message.put("filename", fileName);
        String uploadURL = documentService.createUploadURL(fileName);
        message.put("uploadURL", uploadURL);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody DocumentUploadDto doc) throws Exception {
        Map<String,String> response= new HashMap<>();
        Document storedDoc;
        try {
            storedDoc = documentService.storeUploadedDocumentInDB(doc);
        } catch (Exception e) {
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(storedDoc, HttpStatus.OK);
    }

    public void printDocument(DocumentUploadDto document) {
        System.out.println("Document Required ID: " + document.getDocumentRequiredId());
        System.out.println("External ID: " + document.getExternalId());
        System.out.println("External Table: " + document.getExternalTable());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocuments() {
        return ResponseEntity.ok()
                .header("Custom-Header", "foo")
                .body(documentService.findAll());
    }
}
