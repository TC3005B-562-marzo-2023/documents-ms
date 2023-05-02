package com.driveai.documentsms.services;

import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRequiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentRequiredService {
    @Autowired
    DocumentRequiredRepository documentRequiredRepository;

    public DocumentRequired saveRequiredDocument(DocumentRequired documentRequired) {
        if (documentRequired.getDocumentRequiredId() == 0) {
            documentRequired = documentRequiredRepository.save(documentRequired);
        }
        return documentRequired;
    }

    public List<DocumentRequiredDto> findAll() {
        List<DocumentRequired> documentRequiredList = documentRequiredRepository.findAll();
        List<DocumentRequiredDto> results = new ArrayList<>();
        for(DocumentRequired d: documentRequiredList) {
            DocumentRequiredDto dto = new DocumentRequiredDto(d);
            results.add(dto);
        }
        return results;
    }

}
