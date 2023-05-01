package com.driveai.documentsms.services;

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
