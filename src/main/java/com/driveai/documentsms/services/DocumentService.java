package com.driveai.documentsms.services;

import ch.qos.logback.core.Context;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.driveai.documentsms.client.UserClient;
import com.driveai.documentsms.config.AwsS3Config;
import com.driveai.documentsms.dto.CreateDocumentDto;
import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.dto.UpdateDocumentDto;
import com.driveai.documentsms.dto.UserDealershipDto;
import com.driveai.documentsms.factory.LogFactory;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRepository;
import com.driveai.documentsms.repositories.DocumentRequiredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    @Autowired
    LogService logService;
    @Autowired
    AwsS3Config awsS3Client;

    private AwsS3Service awsS3Service;
    @Autowired
    public void MainController(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    /*
    public String createUploadURL(String fileName) {
        return awsS3Service.generatePreSignedUrl(fileName, "drive-ai-ccm", HttpMethod.PUT);
    }*/

    public Document findDocumentById(int id, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Found";
        String description = "The user with id "+userId+" found document with id "+id;
        String method = "GET";
        int status = 200;

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRepository.findById(id).orElseThrow(() -> new Exception("Document not found with id: " + id));
    }

    public Document saveDocument(CreateDocumentDto document, String email) throws Exception { //DocumentUploadDto

        UserDealershipDto userDto = userClient.findUserByEmail(email);
        DocumentRequired documentRequired = documentRequiredService.findDocumentRequiredById(document.getDocumentRequiredId(), email);

        int userId = userDto.getId();
        String title = "Document Created";
        String description = "The user with id "+userId+" created document";
        String method = "POST";
        int status = 200;

        if(documentRequired == null) throw new Exception("Document required not found with id: " + document.getDocumentRequiredId());

        /*
        if(userDto.getUser_type().equals("AUTOMOTIVE_GROUP_ADMIN")) {
            documentRequired.setExternalTable("automotive_group");
            //documentRequired.setExternalId(userDto.getDealershipId());
        } else {
            documentRequired.setExternalTable("user");
            documentRequired.setExternalId(userDto.getId());
        }*/

        Document newDoc = new Document();
        newDoc.setExternalId(document.getExternalId());
        newDoc.setExternalTable(document.getExternalTable());
        newDoc.setDocumentRequiredId(documentRequired);
        newDoc.setStorageUrl(document.getStorageUrl());
        newDoc.setCreatedAt(Timestamp.from(Instant.now()));

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRepository.save(newDoc);
    }

    public Document updateDocumentById(int id, UpdateDocumentDto document, String email) throws Exception {

        Optional<Document> documentInDB = documentRepository.findById(id);
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Updated";
        String description = "The user with id "+userId+" updated document with id "+id;
        String method = "PUT";
        int status = 200;

        if (documentInDB.isEmpty())  throw new Exception("Unable to find document with id: " + id);
        if(documentInDB.get().isDeleted()) throw new Exception("Document is deleted");

        documentInDB.get().setStorageUrl(document.getStorageUrl());
        documentInDB.get().setStatus(document.getStatus());
        documentInDB.get().setOcrChecked(document.isOcrChecked());
        documentInDB.get().setUpdatedAt(Timestamp.from(Instant.now()));

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRepository.save(documentInDB.get());
    }

    public String getDocumentStatus(int externalId, String externalTable) {
        return documentRepository.callValidateDocumentsStoredProcedure(externalId, externalTable);
    }

    public List<DocumentDto> findAll(String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Found All";
        String description = "The user with id "+userId+" found all documents";
        String method = "GET";
        int status = 200;

        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> results = new ArrayList<>();
        for(Document d: documentList) {
            DocumentDto dto = new DocumentDto(d);
            results.add(dto);
        }

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return results;
    }

    public List<DocumentDto> getDocumentsForAutomotiveGroup(int id, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Found All For User";
        String description = "The user with id "+userId+" found all documents for user with id "+id;
        String method = "GET";
        int status = 200;

        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> results = new ArrayList<>();
        for(Document d: documentList) {
            if(Objects.equals(d.getExternalId(), id)
                    && !d.isDeleted()
                    && Objects.equals(d.getExternalTable(), "automotive_group")
            ) {
                DocumentDto dto = new DocumentDto(d);
                results.add(dto);
            }
        }

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return results;
    }

    public Document deleteDocumentById(int id, String email) throws Exception {
        Optional<Document> documentInDB = documentRepository.findById(id);
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Deleted";
        String description = "The user with id "+userId+" deleted document with id "+id;
        String method = "DELETE";
        int status = 200;

        if(documentInDB.isEmpty()) throw new Exception("Unable to find document with id: " + id);

        // Luis - Borrar el documento en s3


        // Aqui termina

        documentInDB.get().setDeleted(true);
        documentInDB.get().setDeletedAt(Timestamp.from(Instant.now()));

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return documentRepository.save(documentInDB.get());
    }

    public List<DocumentDto> getDocumentsForUser(int id, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Found All For User";
        String description = "The user with id "+userId+" found all documents for user with id "+id;
        String method = "GET";
        int status = 200;

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

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return results;
    }

    public String uploadFile(String keyName, File file) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        //metadata.setContentLength(file.getSize());
            //awsS3Client.putObject("drive-ai-ccm", keyName, file.getInputStream(), metadata);

        return "File not uploaded: " + keyName;
    }

    public int findDocumentIdByUrl(String url) {
        Document document = documentRepository.findByStorageUrl(url);
        if (document != null) {
            return document.getDocumentId();
        } else {
            return -1;
        }
    }



}
