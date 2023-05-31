package com.driveai.documentsms.controllers;

import com.driveai.documentsms.dto.CreateDocumentRequiredDto;
import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.dto.UpdateDocumentRequiredDto;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.services.DocumentRequiredService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/document-required")
public class DocumentRequiredController {
    @Autowired
    DocumentRequiredService documentRequiredService;
    @ApiResponse(responseCode = "200", description = "List of all required documents", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =  DocumentRequiredDto.class))
    })
    @GetMapping("/list")
    public ResponseEntity<?> listDocumentRequired(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentRequiredService.findAll(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/create")
    @ApiResponse(responseCode = "201", description = "Document Required created", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentRequiredDto.class) )
    })
    public ResponseEntity<?> createRequiredDocument(@RequestBody CreateDocumentRequiredDto documentRequired, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentRequiredService.saveRequiredDocument(documentRequired, email), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Document Required updated", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentRequiredDto.class) )
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDocumentRequired(@PathVariable("id") int id, @RequestBody UpdateDocumentRequiredDto documentRequired, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentRequiredService.updateDocumentRequiredById(id, documentRequired, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Document Required found", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentRequiredDto.class) )
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findByIdDocumentRequired(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token=(JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentRequiredService.findDocumentRequiredById(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Document Required deleted", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentRequiredDto.class) )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DocumentRequired> deleteDocumentRequired(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentRequiredService.deleteDocumentRequiredById(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "List of Document Required for test drive", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentRequiredDto.class) )
    })
    @GetMapping("/get-documents-required-for-test-drive/{id}")
    public ResponseEntity<?> forDemo(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token=(JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentRequiredService.getDocumentsRequiredForTestDrive(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "List of Document Required for sale", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentRequiredDto.class) )
    })
    @GetMapping("/get-documents-required-for-sale/{id}")
    public ResponseEntity<?> forSale(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentRequiredService.getDocumentsRequiredForSale(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
