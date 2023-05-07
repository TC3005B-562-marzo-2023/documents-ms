package com.driveai.documentsms.dto;

import com.driveai.documentsms.models.Log;

import java.sql.Timestamp;

public class LogDto {
    private int logId;
    private int userId;
    private String title;
    private String description;
    private  String procedureAction;
    private int statusCode;
    private Timestamp createdAt;

    /**
     * SETTERS
     */
    public void setLogId(int logId) {
        this.logId = logId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProcedureAction(String procedureAction) {
        this.procedureAction = procedureAction;
    }

    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * GETTERS
     */
    public int getLogId() {
        return logId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getProcedureAction() {
        return procedureAction;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public LogDto(Log log) {
        this.logId = log.getLogId();
        this.userId = log.getUserId();
        this.title = log.getTitle();
        this.description = log.getDescription();
        this.procedureAction = log.getProcedureAction();
        this.statusCode = log.getStatusCode();
        this.createdAt = log.getCreatedAt();
    }
}
