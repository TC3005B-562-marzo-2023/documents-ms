package com.driveai.documentsms.models;

import com.driveai.documentsms.dto.DocumentUploadDto;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id", updatable = false)
    @JsonView(Views.Get.class)
    private int documentId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_required_id", referencedColumnName = "document_required_id", nullable = false)
    @JsonView(Views.Get.class)
    private DocumentRequired documentRequiredId;
    @Column(name = "external_id", nullable = false)
    private int externalId;
    @Column(name = "external_table", nullable = false)
    private String externalTable;
    @Column(name = "storage_url", nullable = false)
    private String storageUrl;
    @Column(name = "status")
    private String status = "pending";
    @Column(name = "ocr_checked")
    private boolean ocrChecked;
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

    public Document(DocumentUploadDto doc) {
        this.externalId = doc.getExternalId();
        this.externalTable = doc.getExternalTable();
        this.storageUrl = doc.getStorageUrl();
    }

    public Document() {

    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public void setDocumentRequiredId(DocumentRequired documentRequiredId) { this.documentRequiredId = documentRequiredId; }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public void setExternalTable(String externalTable) {
        this.externalTable = externalTable;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOcrChecked(Boolean ocrChecked) {
        this.ocrChecked = ocrChecked;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getDocumentId() {
        return documentId;
    }

    public DocumentRequired getDocumentRequiredId() {
        return documentRequiredId;
    }

    public int getExternalId() {
        return externalId;
    }

    public String getExternalTable() {
        return externalTable;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getOcrChecked() {
        return ocrChecked;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

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
