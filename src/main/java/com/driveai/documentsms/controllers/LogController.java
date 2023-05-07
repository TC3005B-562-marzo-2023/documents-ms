package com.driveai.documentsms.controllers;

import com.driveai.documentsms.dto.LogDto;
import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/log")
public class LogController {
    @Autowired
    LogService logService;
    @GetMapping("/list")
    public ResponseEntity<List<LogDto>> getAllDocumentRequired(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(logService.findAll(email),HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Log> findByIdLog(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        return new ResponseEntity<>(logService.findById(id, email),HttpStatus.OK);
    }
}
