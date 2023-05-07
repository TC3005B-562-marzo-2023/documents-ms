package com.driveai.documentsms.controllers;

import com.driveai.documentsms.dto.CreateDocumentRequiredDto;
import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.dto.UpdateDocumentRequiredDto;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.services.DocumentRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/document-required")
public class DocumentRequiredController {
    @Autowired
    DocumentRequiredService documentRequiredService;
    @GetMapping("/list")
    public ResponseEntity<List<DocumentRequiredDto>> listDocumentRequired(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentRequiredService.findAll(email),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<DocumentRequired> createRequiredDocument(@RequestBody CreateDocumentRequiredDto documentRequired, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentRequiredService.saveRequiredDocument(documentRequired, email), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<DocumentRequired> updateDocumentRequired(@PathVariable("id") int id, @RequestBody UpdateDocumentRequiredDto documentRequired, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentRequiredService.updateDocumentRequiredById(id, documentRequired, email), HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<DocumentRequired> findByIdDocumentRequired(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token=(JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentRequiredService.findDocumentRequiredById(id, email),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DocumentRequired> deleteDocumentRequired(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentRequiredService.deleteDocumentRequiredById(id, email),HttpStatus.OK);
    }
    @GetMapping("/get-documents-required-for-test-drive/{id}")
    public ResponseEntity<List<DocumentRequiredDto>> forDemo(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token=(JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentRequiredService.getDocumentsRequiredForTestDrive(id, email),HttpStatus.OK);
    }
    @GetMapping("/get-documents-required-for-sale/{id}")
    public ResponseEntity<List<DocumentRequiredDto>> forSale(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentRequiredService.getDocumentsRequiredForSale(id, email), HttpStatus.OK);
    }
}
