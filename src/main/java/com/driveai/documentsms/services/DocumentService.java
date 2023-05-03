package com.driveai.documentsms.services;

import com.amazonaws.HttpMethod;
import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.dto.DocumentUploadDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentRequiredService documentRequiredService;

    private AwsS3Service awsS3Service;
    @Autowired
    public void MainController(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    public String createUploadURL(String fileName) {
        return awsS3Service.generatePreSignedUrl(fileName, "drive-ai-ccm", HttpMethod.PUT);
    }

    public Document storeUploadedDocumentInDB(DocumentUploadDto doc) throws Exception {
        Document documentToSave = new Document(doc);
        DocumentRequired documentRequired = documentRequiredService.findById(doc.getDocumentRequiredId());
        documentToSave.setDocumentRequiredId(documentRequired);
        printDocument(documentToSave);
        return documentRepository.save(documentToSave);
    }

    public void printDocument(Document document) {
        System.out.println("Document ID: " + document.getDocumentId());
        System.out.println("Document Required ID: " + document.getDocumentRequiredId());
        System.out.println("External ID: " + document.getExternalId());
        System.out.println("External Table: " + document.getExternalTable());
        System.out.println("Storage URL: " + document.getStorageUrl());
        System.out.println("Status: " + document.getStatus());
        System.out.println("Created At: " + document.getCreatedAt());
        System.out.println("Updated At: " + document.getUpdatedAt());
        System.out.println("Is Deleted: " + document.isDeleted());
        System.out.println("Deleted At: " + document.getDeletedAt());
    }
    public List<DocumentDto> findAll() {
        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> results = new ArrayList<>();
        for(Document d: documentList) {
            DocumentDto dto = new DocumentDto(d);
            results.add(dto);
        }
        return results;
    }
}
