package com.driveai.documentsms.controllers;

import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.dto.CreateDocumentDto;
import com.driveai.documentsms.dto.UpdateDocumentDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.services.DocumentService;
import com.driveai.documentsms.services.ImageParse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @PostMapping("/get-ocr")
    public ResponseEntity<?> getOcr(Principal principal, @RequestParam(value = "file") MultipartFile file) throws IOException {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(ImageParse.parseImage(file), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "List of all documents", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @GetMapping("/list")
    public ResponseEntity<?> getAllDocuments(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.findAll(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "List of all Automotive Group documents", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @GetMapping("/get-documents-for-automotive-group/{id}")
    public ResponseEntity<?> getDocumentsForAutomotiveGroup(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.getDocumentsForAutomotiveGroup(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Document created", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody CreateDocumentDto document, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.saveDocument(document, email), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Document updated", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable("id") int id, @RequestBody UpdateDocumentDto document, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.updateDocumentById(id, document, email), HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Document found", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findDocumentById(@PathVariable int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.findDocumentById(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Document deleted", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDocumentById(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.deleteDocumentById(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "List of all documents for user", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @GetMapping("/get-documents-for-user/{id}")
    public ResponseEntity<?> getDocumentsForUser(@PathVariable int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.getDocumentsForUser(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Get required document status", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DocumentDto.class))
    })
    @GetMapping("/get-req-document-status")
    public ResponseEntity<?> getDocumentStatus(@RequestParam("id") int externalId, @RequestParam("table") String externalTable, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(documentService.getDocumentStatus(externalId, externalTable, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
