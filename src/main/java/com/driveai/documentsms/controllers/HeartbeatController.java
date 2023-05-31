package com.driveai.documentsms.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/heartbeat")
public class HeartbeatController {

    @ApiResponse(responseCode = "200", description = "Service is active")
    @GetMapping("/heartbeat")
    public ResponseEntity<?> heartbeat() throws Exception {
        try {
            return ResponseEntity.ok("Service is active!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
