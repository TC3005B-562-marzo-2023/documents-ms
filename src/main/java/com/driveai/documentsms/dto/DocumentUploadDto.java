package com.driveai.documentsms.dto;

public class DocumentUploadDto {
    private int documentRequiredId;
    private int externalId;
    private String externalTable;
    private String storageUrl;

    public int getDocumentRequiredId() {
        return documentRequiredId;
    }

    public void setDocumentRequiredId(int documentRequiredId) {
        this.documentRequiredId = documentRequiredId;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public String getExternalTable() {
        return externalTable;
    }

    public void setExternalTable(String externalTable) {
        this.externalTable = externalTable;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }
}
