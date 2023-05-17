package com.driveai.documentsms.dto;

public class DocumentUploadDto {
    private int documentRequiredId;
    private int externalId;
    private String externalTable;
    private String storageUrl;

    /**
     * SETTERS
     */
    public void setDocumentRequiredId(int documentRequiredId) {
        this.documentRequiredId = documentRequiredId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public void setExternalTable(String externalTable) {
        this.externalTable = externalTable;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    /**
     * GETTERS
     */
    public int getDocumentRequiredId() {
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
}
