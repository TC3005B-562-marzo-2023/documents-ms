package com.driveai.documentsms.services;

import com.amazonaws.HttpMethod;
import com.driveai.documentsms.client.UserClient;
import com.driveai.documentsms.dto.CreateDocumentDto;
import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.dto.UpdateDocumentDto;
import com.driveai.documentsms.dto.UserDealershipDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRepository;
import com.driveai.documentsms.repositories.DocumentRequiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentRequiredService documentRequiredService;
    @Autowired
    DocumentRequiredRepository documentRequiredRepository;
    @Autowired
    UserClient userClient;

    private AwsS3Service awsS3Service;
    @Autowired
    public void MainController(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    public String createUploadURL(String fileName) {
        return awsS3Service.generatePreSignedUrl(fileName, "drive-ai-ccm", HttpMethod.PUT);
    }

    public Document findDocumentById(int documentId, String email) throws Exception {
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);
        return documentRepository.findById(documentId).orElseThrow(() -> new Exception("Document not found with id: " + documentId));
    }

    public Document saveDocument(CreateDocumentDto document, String email) throws Exception { //DocumentUploadDto
        DocumentRequired documentRequired = documentRequiredService.findDocumentRequiredById(document.getDocumentRequiredId(), email);
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);

        if(documentRequired == null) throw new Exception("Document required not found with id: " + document.getDocumentRequiredId());

        /*
        if(userDealershipDto.getUser_type().equals("AUTOMOTIVE_GROUP_ADMIN")) {
            documentRequired.setExternalTable("automotive_group");
            //documentRequired.setExternalId(userDealershipDto.getDealershipId());
        } else {
            documentRequired.setExternalTable("user");
            documentRequired.setExternalId(userDealershipDto.getId());
        }*/

        Document newDoc = new Document();
        newDoc.setDocumentRequiredId(documentRequired);
        newDoc.setStorageUrl(document.getStorageUrl());
        newDoc.setCreatedAt(Timestamp.from(Instant.now()));

        return documentRepository.save(newDoc);
    }

    public Document updateDocumentById(int id, UpdateDocumentDto document, String email) throws Exception {
        Optional<Document> documentInDB = documentRepository.findById(id);
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);

        if (documentInDB.isEmpty())  throw new Exception("Unable to find document with id: " + id);
        if(documentInDB.get().isDeleted()) throw new Exception("Document is deleted");

        documentInDB.get().setStorageUrl(document.getStorageUrl());
        documentInDB.get().setStatus(document.getStatus());
        documentInDB.get().setOcrChecked(document.isOcrChecked());
        documentInDB.get().setUpdatedAt(Timestamp.from(Instant.now()));

        return documentRepository.save(documentInDB.get());
    }

    public String getDocumentStatus(int externalId, String externalTable) {
        return documentRepository.callValidateDocumentsStoredProcedure(externalId, externalTable);
    }

    public List<DocumentDto> findAll(String email) throws Exception {
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);

        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> results = new ArrayList<>();
        for(Document d: documentList) {
            DocumentDto dto = new DocumentDto(d);
            results.add(dto);
        }
        return results;
    }

    public Document deleteDocumentById(int id, String email) throws Exception {
        Optional<Document> documentInDB = documentRepository.findById(id);
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);

        if(documentInDB.isEmpty()) throw new Exception("Unable to find document with id: " + id);

        documentInDB.get().setDeleted(true);
        documentInDB.get().setDeletedAt(Timestamp.from(Instant.now()));
        return documentRepository.save(documentInDB.get());
    }

    public List<DocumentDto> getDocumentsForUser(int id, String email) throws Exception {
        UserDealershipDto userDealershipDto = userClient.findUserByEmail(email);

        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> results = new ArrayList<>();
        for(Document d: documentList) {
            if(Objects.equals(d.getExternalId(), id)
                    && !d.isDeleted()
                    && Objects.equals(d.getExternalTable(), "user")
            ) {
                DocumentDto dto = new DocumentDto(d);
                results.add(dto);
            }
        }
        return results;
    }
}
