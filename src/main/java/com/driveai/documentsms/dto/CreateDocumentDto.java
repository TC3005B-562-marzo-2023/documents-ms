package com.driveai.documentsms.dto;

public class CreateDocumentDto {
    private int documentRequiredId;
    private String storageUrl;

    public void setDocumentRequiredId(int documentRequiredId) {
        this.documentRequiredId = documentRequiredId;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public int getDocumentRequiredId() {
        return documentRequiredId;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

}
