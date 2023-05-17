package com.driveai.documentsms.services;

import com.driveai.documentsms.dto.LogDto;
import com.driveai.documentsms.models.Log;
import com.driveai.documentsms.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;
    public Log findById(int id, String email) throws Exception {
        return logRepository.findById(id).orElseThrow(()
                -> new Exception("Log not found with id: " + id));
    }

    public void saveLog(Log log) throws Exception {
        if (log.getLogId() != 0) throw new Exception("Cannot pass the primary id as a parameter");
        log.setCreatedAt(Timestamp.from(Instant.now()));
        logRepository.save(log);
    }

    public List<LogDto> findAll(String email) {
        List<Log> logList = logRepository.findAll();
        List<LogDto> results = new ArrayList<>();
        for(Log l: logList) {
            LogDto dto = new LogDto(l);
            results.add(dto);
        }
        return results;
    }
}
