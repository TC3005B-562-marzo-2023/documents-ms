package com.driveai.documentsms.services;

import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRequiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentRequiredService {
    @Autowired
    DocumentRequiredRepository documentRequiredRepository;

    public DocumentRequired findById(int id) throws Exception {
        return documentRequiredRepository.findById(id).orElseThrow(()
                -> new Exception("Document Required not found with id: " + id));
    }

    public DocumentRequired saveRequiredDocument(DocumentRequired documentRequired) {
        if (documentRequired.getDocumentRequiredId() == 0) {
            documentRequired = documentRequiredRepository.save(documentRequired);
        }
        return documentRequired;
    }
    public DocumentRequired updateDocumentRequiredById(int id, DocumentRequired documentRequired) throws Exception {
        Optional<DocumentRequired> documentInDB = documentRequiredRepository.findById(id);
        if (documentInDB.isEmpty()) { //Check if documentRequired exists
            throw new Exception("Unable to find document with id: " + id);
        }
        else { //If document Required exists are changes being made? If not, launch an exception.
            DocumentRequired originalDocument = documentInDB.get();
            if (documentRequired.getDocumentName().equals(originalDocument.getDocumentName()) &&
                    documentRequired.getExternalTable().equals(originalDocument.getExternalTable()) &&
                    documentRequired.getDocumentNote().equals(originalDocument.getDocumentNote()) &&
                    documentRequired.getDocumentFormat().equals(originalDocument.getDocumentFormat()) &&
                    documentRequired.getProcessType().equals(originalDocument.getProcessType())) {
                throw new Exception("Unable to update document with id '" + id + "', since no data was changed.");
            }
        }
        documentRequired.setDocumentRequiredId(id);
        documentRequired = documentRequiredRepository.save(documentRequired);
        return documentRequired;//Save document object
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
