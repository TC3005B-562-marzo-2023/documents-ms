package com.driveai.documentsms.dto;

public class UpdateDocumentRequiredDto {
    private String documentName;
    private String documentNote;
    private String documentFormat;
    private String processType;

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
