package com.driveai.documentsms.controllers;

import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.factory.LogFactory;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.models.Views;
import com.driveai.documentsms.services.DocumentRequiredService;
import com.driveai.documentsms.services.LogService;
import com.fasterxml.jackson.annotation.JsonView;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/document-required")
public class DocumentRequiredController {
    @Autowired
    DocumentRequiredService documentRequiredService;

    @Autowired
    LogService logService;

    @GetMapping("/list")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> listDocumentRequired(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Required List";
        String description = "The user with id "+userId+" listed document required";
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentRequiredService.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    //@JsonView(Views.Post.class)
    public ResponseEntity<?> createRequiredDocument(@RequestBody DocumentRequired documentRequired, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Required Created";
        String description = "The user with id "+userId+" created document required";
        String method = "POST";
        int status = 201;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentRequiredService.saveRequiredDocument(documentRequired), HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{id}")
    //@JsonView(Views.Put.class)
    public ResponseEntity<?> updateDocumentRequired(@PathVariable("id") int id, @RequestBody DocumentRequired documentRequired, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Required Updated";
        String description = "The user with id "+userId+" updated document required";
        String method = "PUT";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentRequiredService.updateDocumentRequiredById(id, documentRequired), HttpStatus.OK);
        }
        catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/find/{id}")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> findByIdDocumentRequired(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token=(JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Required Found";
        String description = "The user with id "+userId+" found document required";
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentRequiredService.findDocumentRequiredById(id),HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{id}")
    //@JsonView(Views.Delete.class)
    public ResponseEntity<?> deleteDocumentRequired(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Required Deleted";
        String description = "The user with id "+userId+" deleted document required";
        String method = "DELETE";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentRequiredService.deleteDocumentRequiredById(id),HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getDocumentsRequiredForTestDrive/{id}")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> forDemo(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token=(JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Required For Test Drive";
        String description = "The user with id "+userId+" found document required for test drive";
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(documentRequiredService.getDocumentsRequiredForTestDrive(id),HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getDocumentsRequiredForSale/{id}")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> forSale(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        Jwt principalJwt = (Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Document Required For Sale";
        String description = "The user with id " + userId + " found document required for sale";
        String method = "GET";
        int status = 200;

        try {
            logService.saveLog(LogFactory.createLog(userId, title, description, method, status));
            return new ResponseEntity<>(documentRequiredService.getDocumentsRequiredForSale(id), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            logService.saveLog(LogFactory.createLog(userId, title, description, method, 400));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
