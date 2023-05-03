package com.driveai.documentsms.services;

import com.amazonaws.HttpMethod;
import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.models.Document;
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
