package com.driveai.documentsms.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false, updatable = false)
    @JsonView(Views.Get.class)
    private int logId;
    @Column(name = "user_id", nullable = false, updatable = false)
    private int userId;
    @Column(name = "title", nullable = false, updatable = false)
    private String title;
    @Column(name = "description", nullable = false, updatable = false)
    private String description;
    @Column(name = "procedure_action", nullable = false, updatable = false)
    private  String procedureAction;
    @Column(name = "status_code", nullable = false, updatable = false)
    private int statusCode;
    @Column(name = "created_at", updatable = false)
    @JsonView(Views.Get.class)
    private Timestamp createdAt;

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

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

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
}
