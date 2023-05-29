package com.driveai.documentsms.controllers;

import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.dto.LogDto;
import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.services.LogService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @ApiResponse(responseCode = "200", description = "List of all logs", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =  LogDto.class))
    })
    @GetMapping("/list")
    public ResponseEntity<?> getAllDocumentRequired(Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(logService.findAll(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @ApiResponse(responseCode = "200", description = "Log found", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LogDto.class) )
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findByIdLog(@PathVariable("id") int id, Principal principal) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)principal;
        Jwt principalJwt=(Jwt) token.getPrincipal();
        String email = principalJwt.getClaim("email");
        try {
            return new ResponseEntity<>(logService.findById(id, email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
