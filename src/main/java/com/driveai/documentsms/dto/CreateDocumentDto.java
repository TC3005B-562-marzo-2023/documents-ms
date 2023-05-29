package com.driveai.documentsms.dto;

public class CreateDocumentDto {
    private int externalId;
    private String externalTable;
    private int documentRequiredId;
    private String storageUrl;

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public void setExternalTable(String externalTable) {
        this.externalTable = externalTable;
    }

    public void setDocumentRequiredId(int documentRequiredId) {
        this.documentRequiredId = documentRequiredId;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public int getExternalId() {
        return externalId;
    }

    public String getExternalTable() {
        return externalTable;
    }

    public int getDocumentRequiredId() {
        return documentRequiredId;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

}
