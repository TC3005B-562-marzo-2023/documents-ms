package com.driveai.documentsms.controllers;

import com.driveai.documentsms.factory.LogFactory;
import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.models.Views;
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

@RestController
@RequestMapping("/v1/log")
public class LogController {
    @Autowired
    LogService logService;
    @GetMapping("/list")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> getAllDocumentRequired(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Log List";
        String description = "The user with id "+userId+" listed logs";
        String method = "GET";
        int status = 200;

        try {
            //logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(logService.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            //logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{id}")
    //@JsonView(Views.Get.class)
    public ResponseEntity<?> findByIdLog(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();

        int userId = 1;//Integer.parseInt(principalJwt.getClaim("user_id"));
        String title = "Log Found";
        String description = "The user with id "+userId+" found a log";
        String method = "GET";
        int status = 200;

        try {
            //logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
            return new ResponseEntity<>(logService.findById(id),HttpStatus.OK);
        } catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("message", e.getMessage());
            //logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
