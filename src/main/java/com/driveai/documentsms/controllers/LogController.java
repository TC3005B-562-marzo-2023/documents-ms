package com.driveai.documentsms.controllers;

import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/v1/log")
public class LogController {
    @Autowired
    LogService logService;
}
