package com.driveai.documentsms.services;

import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRequiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public DocumentRequired updateDocumentRequired(DocumentRequired documentRequired) throws Exception {
        Optional<DocumentRequired> documentInDB = documentRequiredRepository.findById(documentRequired.getDocumentRequiredId());
        if (!documentInDB.isPresent()) { //Check if documentRequired exists
            throw new Exception("Unable to find document with id: "+ documentRequired.getDocumentRequiredId());
        }
        else if (documentInDB.isPresent()){ //If documetRequired exists are changes being made? If not, launch an exception.
            DocumentRequired originalDocument = documentInDB.get();
            if(documentRequired.getDocumentName().equals(originalDocument.getDocumentName()) &&
                documentRequired.getExternalTable().equals(originalDocument.getExternalTable()) &&
                documentRequired.getDocumentNote().equals(originalDocument.getDocumentNote()) &&
                documentRequired.getDocumentFormat().equals(originalDocument.getDocumentFormat()) &&
                documentRequired.getProcessType().equals(originalDocument.getProcessType())){
                throw new Exception("Unable to update document with id: "+ documentRequired.getDocumentRequiredId() + ", since no data was changed.");
            }
        }
        documentRequired = documentRequiredRepository.save(documentRequired);

        return documentRequired; //Save document object
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
