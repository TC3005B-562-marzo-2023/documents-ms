package com.driveai.documentsms.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class HeartbeatController {

    @GetMapping("/heartbeat")
    public ResponseEntity<?> heartbeat()
    {return ResponseEntity.ok("Service is active!");}
}
