package com.driveai.documentsms.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class DocumentRequired {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_required_id", updatable = false)
    @JsonView(Views.Get.class)
    private int documentRequiredId;
    @Column(name = "external_id", updatable = false)
    private int externalId;
    @Column(name = "external_table", nullable = false)
    private String externalTable;
    @Column(name = "document_name", nullable = false)
    private String documentName;
    @Column(name = "document_note")
    private String documentNote;
    @Column(name = "document_format", nullable = false)
    private String documentFormat;
    @Column(name = "process_type", nullable = false)
    private String processType;
    @Column(name = "created_at", updatable = false)
    @JsonView(Views.Get.class)
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @JsonView(Views.Get.class)
    private Timestamp updatedAt;
    @Column(name = "is_deleted")
    @JsonView(Views.Get.class)
    private boolean isDeleted;
    @Column(name = "deleted_at")
    @JsonView(Views.Get.class)
    private Timestamp deletedAt;

    public void setDocumentRequiredId(int documentRequiredId) {this.documentRequiredId = documentRequiredId; }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public void setExternalTable(String externalTable) {
        this.externalTable = externalTable;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setDocumentNote(String documentNote) {
        this.documentNote = documentNote;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(boolean deleted) {isDeleted = deleted; }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getDocumentRequiredId() {
        return documentRequiredId;
    }

    public int getExternalId() {
        return externalId;
    }

    public String getExternalTable() {
        return externalTable;
    }

    public String getDocumentName() {
        return documentName;
    }

    public String getDocumentNote() {
        return documentNote;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public String getProcessType() {
        return processType;
    }

    public Timestamp getCreatedAt() { return createdAt; }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }
}
