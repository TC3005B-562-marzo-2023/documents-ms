package com.driveai.documentsms.controllers;

import com.amazonaws.HttpMethod;
import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.dto.CreateDocumentDto;
import com.driveai.documentsms.dto.UpdateDocumentDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.services.AwsS3Service;
import com.driveai.documentsms.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/v1/document")
public class DocumentController {
    final
    DocumentService documentService;
    private final AwsS3Service awsS3Service;
    public DocumentController(AwsS3Service awsS3Service, DocumentService documentService) {
        this.awsS3Service = awsS3Service;
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
    @GetMapping("/get-req-document-status")
    public ResponseEntity<String> getDocumentStatus(@RequestParam("id") int externalId, @RequestParam("table") String externalTable, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.getDocumentStatus(externalId, externalTable), HttpStatus.OK);
    }
    @GetMapping("/get-upload-url")
    public ResponseEntity<String> getUploadURL(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");

        String fileName = String.valueOf(UUID.randomUUID());
        HashMap<String, String> message = new HashMap<>();
        message.put("filename", fileName);
        String uploadURL = documentService.createUploadURL(fileName);
        message.put("uploadURL", uploadURL);
        return new ResponseEntity<>(documentService.createUploadURL(fileName), HttpStatus.OK);
    }
    @GetMapping("/get-documents-for-user/{id}")
    public ResponseEntity<List<DocumentDto>> getDocumentsForUser(@PathVariable int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(documentService.getDocumentsForUser(id, email), HttpStatus.OK);
    }
    @GetMapping("/generate-upload-url")
    public ResponseEntity<String> generateUploadUrl() {
        return ResponseEntity.ok(
                awsS3Service.generatePreSignedUrl(UUID.randomUUID()+".png", "drive-ai-ccm", HttpMethod.PUT));
    }

    /*
    @PostMapping("/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("fileName") String fileName,
                                             @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(documentService.uploadFile(fileName, file), HttpStatus.OK);
    }

     */

}
