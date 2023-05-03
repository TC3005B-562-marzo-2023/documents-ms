package com.driveai.documentsms.controllers;

import com.driveai.documentsms.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/get-req-document-status")
    public ResponseEntity<?> getDocumentStatus(@RequestParam("id") int externalId, @RequestParam("table") String externalTable) {
        return ResponseEntity.ok().body(documentService.getDocumentStatus(externalId, externalTable));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocuments() {
        return ResponseEntity.ok()
                .header("Custom-Header", "foo")
                .body(documentService.findAll());
    }
}
