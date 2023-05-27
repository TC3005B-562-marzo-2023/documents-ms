package com.driveai.documentsms.dto;

public class CreateDocumentRequiredDto {
    private int externalId;
    private String externalTable;
    private String documentName;
    private String documentNote;
    private String documentFormat;
    private String processType;

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
}
