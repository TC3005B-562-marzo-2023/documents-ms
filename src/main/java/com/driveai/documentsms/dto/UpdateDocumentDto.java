package com.driveai.documentsms.dto;

public class UpdateDocumentDto {
    private String storageUrl;
    private String status;
    private boolean ocrChecked;

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOcrChecked(boolean ocrChecked) {
        this.ocrChecked = ocrChecked;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public String getStatus() {
        return status;
    }

    public boolean isOcrChecked() {
        return ocrChecked;
    }
}
