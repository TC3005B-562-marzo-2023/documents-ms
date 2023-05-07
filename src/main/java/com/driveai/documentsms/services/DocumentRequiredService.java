package com.driveai.documentsms.services;

import com.driveai.documentsms.client.UserClient;
import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.dto.UserDealershipDto;
import com.driveai.documentsms.factory.LogFactory;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRequiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.Timestamp;
import java.time.Instant;
@Service
public class DocumentRequiredService {
    @Autowired
    DocumentRequiredRepository documentRequiredRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    LogService logService;

    public DocumentRequired findDocumentRequiredById(int id, String email) throws Exception {
        return documentRequiredRepository.findById(id).orElseThrow(() -> new Exception("Document Required not found with id: " + id));
    }

    public DocumentRequired saveRequiredDocument(DocumentRequired documentRequired, String email) throws Exception {
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);

        int userId = userDealershipDto.getId();
        String title = "Document Required Created";
        String description = "The user with id "+userId+" created document required";
        String method = "POST";
        int status = 201;

        if(userDealershipDto.getUser_type().equals("MANAGER")) {
            documentRequired.setExternalTable("dealership");
            //documentRequired.setExternalId(userDealershipDto.getDealershipId());
        } else {
            documentRequired.setExternalTable("user");
            documentRequired.setExternalId(userDealershipDto.getId());
        }

        documentRequired.setCreatedAt(Timestamp.from(Instant.now()));

        if (documentRequired.getDocumentRequiredId() != 0) {
            logService.saveLog(LogFactory.createLog(userId,title,description,method,400));
            throw new Exception("Cannot pass the primary id as a parameter");
        }

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
        return documentRequiredRepository.save(documentRequired);
    }

    public DocumentRequired updateDocumentRequiredById(int id, DocumentRequired documentRequired, String email) throws Exception {
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

    public List<DocumentRequiredDto> findAll(String email) throws Exception {
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);

        List<DocumentRequired> documentRequiredList = documentRequiredRepository.findAll();
        List<DocumentRequiredDto> results = new ArrayList<>();
        for(DocumentRequired d: documentRequiredList) {
            DocumentRequiredDto dto = new DocumentRequiredDto(d);
            results.add(dto);
        }
        return results;
    }

    public DocumentRequired deleteDocumentRequiredById(int id, String email) throws Exception {
        Optional<DocumentRequired> documentInDB = documentRequiredRepository.findById(id);

        if(documentInDB.isEmpty()) throw new Exception("Unable to find document with id: " + id);

        documentInDB.get().setDeleted(true);
        documentInDB.get().setDeletedAt(Timestamp.from(Instant.now()));
        return documentRequiredRepository.save(documentInDB.get());
    }
    public List<DocumentRequiredDto> getDocumentsRequiredForTestDrive(int id, String email) throws Exception {
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

    public List<DocumentRequiredDto> getDocumentsRequiredForSale(int id, String email) throws Exception {
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
