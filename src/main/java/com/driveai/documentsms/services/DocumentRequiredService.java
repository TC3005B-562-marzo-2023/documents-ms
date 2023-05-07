package com.driveai.documentsms.services;

import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRequiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.sql.Timestamp;
import java.time.Instant;
@Service
public class DocumentRequiredService {
    @Autowired
    DocumentRequiredRepository documentRequiredRepository;

    public DocumentRequired findDocumentRequiredById(int id) throws Exception {
        return documentRequiredRepository.findById(id).orElseThrow(() -> new Exception("Document Required not found with id: " + id));
    }

    public DocumentRequired saveRequiredDocument(DocumentRequired documentRequired) throws Exception {
        if (documentRequired.getDocumentRequiredId() != 0) throw new Exception("Cannot pass the primary id as a parameter");
        documentRequired.setCreatedAt(Timestamp.from(Instant.now()));
        return documentRequiredRepository.save(documentRequired);
    }

    public DocumentRequired updateDocumentRequiredById(int id, DocumentRequired documentRequired) throws Exception {
        Optional<DocumentRequired> documentInDB = documentRequiredRepository.findById(id);

        if (documentInDB.isEmpty()) throw new Exception("Unable to find document with id: " + id);
        if(documentInDB.get().isDeleted()) throw new Exception("Document is deleted");

        documentRequired.setDocumentRequiredId(id);
        documentRequired.setCreatedAt(documentInDB.get().getCreatedAt());
        documentRequired.setDeletedAt(documentInDB.get().getDeletedAt());
        documentRequired.setDeleted(documentInDB.get().isDeleted());
        documentRequired.setUpdatedAt(Timestamp.from(Instant.now()));

        return documentRequiredRepository.save(documentRequired);
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

    public DocumentRequired deleteDocumentRequiredById(int id) throws Exception {
        Optional<DocumentRequired> documentInDB = documentRequiredRepository.findById(id);

        if(documentInDB.isEmpty()) throw new Exception("Unable to find document with id: " + id);

        documentInDB.get().setDeleted(true);
        documentInDB.get().setDeletedAt(Timestamp.from(Instant.now()));
        return documentRequiredRepository.save(documentInDB.get());
    }
    public List<DocumentRequiredDto> getDocumentsRequiredForTestDrive(int id) throws Exception {
        List<DocumentRequired> documentRequiredList = documentRequiredRepository.findAll();
        List<DocumentRequiredDto> results = new ArrayList<>();
        for(DocumentRequired d: documentRequiredList) {
            if(Objects.equals(d.getProcessType(), "demo")
                    && !d.isDeleted()
                    && Objects.equals(d.getExternalId(), id)
                    && Objects.equals(d.getExternalTable(),"dealership")) {
                DocumentRequiredDto dto = new DocumentRequiredDto(d);
                results.add(dto);
            }
       }
        return results;
    }

    public List<DocumentRequiredDto> getDocumentsRequiredForSale(int id) throws Exception {
        List<DocumentRequired> documentRequiredList = documentRequiredRepository.findAll();
        List<DocumentRequiredDto> results = new ArrayList<>();
        for(DocumentRequired d: documentRequiredList) {
            if(Objects.equals(d.getProcessType(), "sale")
                    && !d.isDeleted()
                    && Objects.equals(d.getExternalId(), id)
                    && Objects.equals(d.getExternalTable(),"dealership")) {
                DocumentRequiredDto dto = new DocumentRequiredDto(d);
                results.add(dto);
            }
        }
        return results;
    }
}
