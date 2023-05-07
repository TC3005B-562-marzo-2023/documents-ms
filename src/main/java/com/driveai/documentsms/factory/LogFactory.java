package com.driveai.documentsms.factory;

import com.driveai.documentsms.models.Log;
import org.springframework.stereotype.Component;

@Component
public class LogFactory {
    public static Log createLog(int userId, String title, String description, String procedureAction, int statusCode) {
        Log log = new Log();
        log.setUserId(userId);
        log.setTitle(title);
        log.setDescription(description);
        log.setProcedureAction(procedureAction);
        log.setStatusCode(statusCode);
        return log;
    }
}
