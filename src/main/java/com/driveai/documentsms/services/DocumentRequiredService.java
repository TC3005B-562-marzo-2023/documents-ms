package com.driveai.documentsms.services;

import com.driveai.documentsms.client.UserClient;
import com.driveai.documentsms.dto.CreateDocumentRequiredDto;
import com.driveai.documentsms.dto.DocumentRequiredDto;
import com.driveai.documentsms.dto.UpdateDocumentRequiredDto;
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
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Required Found";
        String description = "User: "+userId+" found docreq: "+id;
        String method = "GET";
        int status = 200;

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRequiredRepository.findById(id).orElseThrow(() -> new Exception("Document Required not found with id: " + id));
    }

    public DocumentRequired saveRequiredDocument(CreateDocumentRequiredDto documentRequired, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);
        DocumentRequired newDocumentRequired = new DocumentRequired();

        int userId = userDto.getId();
        String title = "Document Required Created";
        String description = "User: "+userId+" created docreq";
        String method = "POST";
        int status = 201;

        /*
        if(userDto.getUser_type().equals("MANAGER")) {
            newDocumentRequired.setExternalTable("dealership");
            //documentRequired.setExternalId(userDto.getDealershipId());
        } else {
            newDocumentRequired.setExternalTable("user");
            newDocumentRequired.setExternalId(userDto.getId());
        }*/

        newDocumentRequired.setExternalId(documentRequired.getExternalId());
        newDocumentRequired.setExternalTable(documentRequired.getExternalTable());
        newDocumentRequired.setCreatedAt(Timestamp.from(Instant.now()));
        newDocumentRequired.setDocumentName(documentRequired.getDocumentName());
        newDocumentRequired.setDocumentNote(documentRequired.getDocumentNote());
        newDocumentRequired.setDocumentFormat(documentRequired.getDocumentFormat());
        newDocumentRequired.setProcessType(documentRequired.getProcessType());

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRequiredRepository.save(newDocumentRequired);
    }

    public DocumentRequired updateDocumentRequiredById(int id, UpdateDocumentRequiredDto documentRequired, String email) throws Exception {
        Optional<DocumentRequired> documentInDB = documentRequiredRepository.findById(id);
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Required Updated";
        String description = "User: "+userId+" put docreq id: "+id;
        String method = "PUT";
        int status = 200;

        if (documentInDB.isEmpty()) throw new Exception("Unable to find document with id: " + id);
        if(documentInDB.get().isDeleted()) throw new Exception("Document is deleted");

        documentInDB.get().setDocumentName(documentRequired.getDocumentName());
        documentInDB.get().setDocumentNote(documentRequired.getDocumentNote());
        documentInDB.get().setDocumentFormat(documentRequired.getDocumentFormat());
        documentInDB.get().setProcessType(documentRequired.getProcessType());
        documentInDB.get().setUpdatedAt(Timestamp.from(Instant.now()));

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRequiredRepository.save(documentInDB.get());
    }

    public List<DocumentRequiredDto> findAll(String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Required Found All";
        String description = "User: "+userId+" got all docreqs";
        String method = "GET";
        int status = 200;

        List<DocumentRequired> documentRequiredList = documentRequiredRepository.findAll();
        List<DocumentRequiredDto> results = new ArrayList<>();
        for(DocumentRequired d: documentRequiredList) {
            DocumentRequiredDto dto = new DocumentRequiredDto(d);
            results.add(dto);
        }

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return results;
    }

    public DocumentRequired deleteDocumentRequiredById(int id, String email) throws Exception {
        Optional<DocumentRequired> documentInDB = documentRequiredRepository.findById(id);
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Required Deleted";
        String description = "User: "+userId+" deleted docreq: "+id;
        String method = "DELETE";
        int status = 200;

        if(documentInDB.isEmpty()) throw new Exception("Unable to find document with id: " + id);

        documentInDB.get().setDeleted(true);
        documentInDB.get().setDeletedAt(Timestamp.from(Instant.now()));

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRequiredRepository.save(documentInDB.get());
    }
    public List<DocumentRequiredDto> getDocumentsRequiredForTestDrive(int id, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "DocumentReq for Test Drive";
        String description = "User: "+userId+" got testdrive docs";
        String method = "GET";
        int status = 200;

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

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return results;
    }

    public List<DocumentRequiredDto> getDocumentsRequiredForSale(int id, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Req Found All Sale";
        String description = "User: "+userId+" found docReq for sale";
        String method = "GET";
        int status = 200;

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


        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
        
        return results;
    }

}
