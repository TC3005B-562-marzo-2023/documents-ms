package com.driveai.documentsms.services;

import com.driveai.documentsms.dto.LogDto;
import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;
    public Log findById(int id) throws Exception {
        return logRepository.findById(id).orElseThrow(()
                -> new Exception("Log not found with id: " + id));
    }

    public List<LogDto> findAll() {
        List<Log> logList = logRepository.findAll();
        List<LogDto> results = new ArrayList<>();
        for(Log l: logList) {
            LogDto dto = new LogDto(l);
            results.add(dto);
        }
        return results;
    }
}
