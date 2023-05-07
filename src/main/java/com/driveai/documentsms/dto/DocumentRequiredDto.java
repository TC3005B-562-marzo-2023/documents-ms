package com.driveai.documentsms.dto;

import com.driveai.documentsms.models.DocumentRequired;

import java.sql.Timestamp;

public class DocumentRequiredDto {
    private int documentRequiredId;
    private int externalId;
    private String externalTable;
    private String documentName;
    private String documentNote;
    private String documentFormat;
    private String processType;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isDeleted;
    private Timestamp deletedAt;
    /**
     * SETTERS
     */
    public void setDocumentRequiredId(int documentRequiredId) { this.documentRequiredId = documentRequiredId; }

    public void setExternalId(int externalId) { this.externalId = externalId; }

    public void setExternalTable(String externalTable) { this.externalTable = externalTable; }

    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public void setDocumentNote(String documentNote) { this.documentNote = documentNote; }

    public void setDocumentFormat(String documentFormat) { this.documentFormat = documentFormat; }

    public void setProcessType(String processType) { this.processType = processType; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public void setDeletedAt(Timestamp deletedAt) { this.deletedAt = deletedAt; }

    /**
     * GETTERS
     */
    public int getDocumentRequiredId() { return documentRequiredId; }

    public int getExternalId() { return externalId; }

    public String getExternalTable() { return externalTable; }

    public String getDocumentName() { return documentName; }

    public String getDocumentNote() { return documentNote; }

    public String getDocumentFormat() { return documentFormat; }

    public String getProcessType() { return processType; }

    public Timestamp getCreatedAt() { return createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }

    public boolean isDeleted() { return isDeleted; }

    public Timestamp getDeletedAt() { return deletedAt; }

    public DocumentRequiredDto(DocumentRequired documentRequired) {
        this.documentRequiredId = documentRequired.getDocumentRequiredId();
        this.externalId = documentRequired.getExternalId();
        this.externalTable = documentRequired.getExternalTable();
        this.documentName = documentRequired.getDocumentName();
        this.documentNote = documentRequired.getDocumentNote();
        this.documentFormat = documentRequired.getDocumentFormat();
        this.processType = documentRequired.getProcessType();
        this.createdAt = documentRequired.getCreatedAt();
        this.updatedAt = documentRequired.getUpdatedAt();
        this.isDeleted = documentRequired.isDeleted();
        this.deletedAt = documentRequired.getDeletedAt();
    }
}
