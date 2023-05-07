package com.driveai.documentsms.controllers;
import com.driveai.documentsms.dto.DocumentDto;
import com.amazonaws.HttpMethod;
import com.driveai.documentsms.dto.DocumentUploadDto;
import com.driveai.documentsms.factory.LogFactory;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.models.Views;
import com.driveai.documentsms.services.DocumentService;
import com.driveai.documentsms.services.LogService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    @Autowired
    LogService logService;

    @GetMapping("/list")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> getAllDocuments(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document List";
        String description = "The user with id "+userId+" listed documents";
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentService.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create") // DocumentUploadDto
    //@JsonView(Views.Post.class)
    public ResponseEntity<?> createDocument(@RequestBody Document document, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Created";
        String description = "The user with id "+userId+" created a document";
        String method = "POST";
        int status = 201;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentService.saveDocument(document), HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{id}")
    //@JsonView(Views.Put.class)
    public ResponseEntity<?> updateDocument(@PathVariable("id") int id, @RequestBody Document document, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Updated";
        String description = "The user with id "+userId+" updated a document";
        String method = "PUT";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentService.updateDocumentById(id, document), HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/find/{id}")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> findDocumentById(@PathVariable int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Found";
        String description = "The user with id "+userId+" found a document";
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentService.findDocumentById(id), HttpStatus.OK);
        }
        catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message",  e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{id}")
    //@JsonView(Views.Delete.class)
    public ResponseEntity<?> deleteDocumentById(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Deleted";
        String description = "The user with id "+userId+" deleted a document";
        String method = "DELETE";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentService.deleteDocumentById(id), HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-req-document-status")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> getDocumentStatus(@RequestParam("id") int externalId, @RequestParam("table") String externalTable, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Status Found";
        String description = "The user with id "+userId+" found a document status";
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentService.getDocumentStatus(externalId, externalTable), HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get-upload-url") // HashMap<String, String>
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> getUploadURL(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Upload URL Created";
        String description = "The user with id "+userId+" created an upload URL";
        String method = "GET";
        int status = 200;

        try {
            String fileName = String.valueOf(UUID.randomUUID());
            HashMap<String, String> message = new HashMap<>();
            message.put("filename", fileName);
            String uploadURL = documentService.createUploadURL(fileName);
            message.put("uploadURL", uploadURL);
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDocumentsForUser/{id}")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> getDocumentsForUser(@PathVariable int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();

        int userId = 1; //Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Documents Found";
        String description = "The user with id " + userId + " found documents for user with id " + id;
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId, title, description, method, status));
            return new ResponseEntity<>(documentService.getDocumentsForUser(id), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId, title, description, method, 400));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
