package com.driveai.documentsms.controllers;

import com.driveai.documentsms.client.OcrClient;
import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.dto.CreateDocumentDto;
import com.driveai.documentsms.dto.UpdateDocumentDto;
import com.driveai.documentsms.services.DocumentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.Principal;
import java.util.*;

@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/v1/document")
public class DocumentController {
    final
    DocumentService documentService;
    @Autowired
    OcrClient ocrClient;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    public Boolean validate(String find, String ocr) {
        int i = ocr.indexOf(find);
        return (i>0);
    }

    @PostMapping(value = "/get-ocr")
    public ResponseEntity<?> getOcr(Principal principal, @RequestParam(value = "image") MultipartFile image) throws IOException {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");

        String language = "eng";
        String str = "CREDENCIAL PARA VOTAR";
        Boolean found = validate(str, ocrClient.getOCR(language, image));
        Map<String, Boolean> results;
        results = new HashMap<>();
        results.put("ocrCheck", found);

        try {
            return new ResponseEntity<>(results, HttpStatus.OK);
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
    @ApiResponse(responseCode = "200", description = "List of all documents for user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))})
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

    @ApiResponse(responseCode = "200", description = "Get required document status", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))})
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

    @ApiResponse(responseCode = "200", description = "Get all documents from specific table and id", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))})
    @GetMapping("/get-documents-from/{externalTable}/{externalId}")
    public ResponseEntity<?> getDocumentsFrom(@PathVariable String externalTable, @PathVariable int externalId, Principal principal) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            List<DocumentDto> documentsFrom = documentService.getDocumentsFrom(externalTable, externalId, email);
            return new ResponseEntity<>(documentsFrom, HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response= new HashMap<>();
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
