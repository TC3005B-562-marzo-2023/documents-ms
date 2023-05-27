package com.driveai.documentsms.controllers;

import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.dto.CreateDocumentDto;
import com.driveai.documentsms.dto.UpdateDocumentDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/v1/document")
public class DocumentController {
    final
    DocumentService documentService;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<DocumentDto>> getAllDocuments(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.findAll(email),HttpStatus.OK);
    }
    @GetMapping("/get-documents-for-automotive-group/{id}")
    public ResponseEntity<List<DocumentDto>> getDocumentsForAutomotiveGroup(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.getDocumentsForAutomotiveGroup(id, email),HttpStatus.OK);
    }
    @PostMapping("/create") // DocumentUploadDto
    public ResponseEntity<Document> createDocument(@RequestBody CreateDocumentDto document, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.saveDocument(document, email), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable("id") int id, @RequestBody UpdateDocumentDto document, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.updateDocumentById(id, document, email), HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Document> findDocumentById(@PathVariable int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.findDocumentById(id, email), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Document> deleteDocumentById(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.deleteDocumentById(id, email), HttpStatus.OK);
    }
    @GetMapping("/get-documents-for-user/{id}")
    public ResponseEntity<List<DocumentDto>> getDocumentsForUser(@PathVariable int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.getDocumentsForUser(id, email), HttpStatus.OK);
    }
    @GetMapping("/get-req-document-status")
    public ResponseEntity<String> getDocumentStatus(@RequestParam("id") int externalId, @RequestParam("table") String externalTable, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.getDocumentStatus(externalId, externalTable), HttpStatus.OK);
    }
}
