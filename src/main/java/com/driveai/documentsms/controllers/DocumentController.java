package com.driveai.documentsms.controllers;
import com.driveai.documentsms.dto.DocumentDto;
import com.amazonaws.HttpMethod;
import com.driveai.documentsms.dto.DocumentUploadDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/get-req-document-status")
    public ResponseEntity<?> getDocumentStatus(@RequestParam("id") int externalId, @RequestParam("table") String externalTable) {
        return ResponseEntity.ok().body(documentService.getDocumentStatus(externalId, externalTable));
    }

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

    @GetMapping("/list")
    public ResponseEntity<?> getAllDocuments() {
        return ResponseEntity.ok()
                .header("Custom-Header", "foo")
                .body(documentService.findAll());
    }

    @GetMapping("/find-by-id/{documentId}")
    public ResponseEntity<?> findDocumentById(@PathVariable int documentId) throws Exception {
        try {
            Document document = documentService.findDocumentById(documentId);
            return new ResponseEntity<>(document, HttpStatus.OK);
        }
        catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", "Document could not be reached: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
