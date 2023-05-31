package com.driveai.documentsms.services;

import com.amazonaws.HttpMethod;
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

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

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

    public Document findDocumentById(int id, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);
        Document document;

        int userId = userDto.getId();
        String title = "Document Found";
        String description = "User: "+userId+" found document: "+id;
        String method = "GET";
        int status = 200;

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            document = optionalDocument.get();
            String newUrl = obtainPreSignedURLFromDocumentToViewOnly(document);
            document.setStorageUrl(newUrl);
            return document;
        } else {
            throw new Exception("Document with id " + id + " not found");
        }
    }

    public Document saveDocument(CreateDocumentDto document, String email) throws Exception { //DocumentUploadDto

        UserDealershipDto userDto = userClient.findUserByEmail(email);
        DocumentRequired documentRequired = documentRequiredService.findDocumentRequiredById(document.getDocumentRequiredId(), email);

        int userId = userDto.getId();
        String title = "Document Created";
        String description = "User: "+userId+" created document";
        String method = "POST";
        int status = 200;

        if(documentRequired == null) throw new Exception("Document required not found with id: " + document.getDocumentRequiredId());

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
        String description = "User: "+userId+" updated document: "+id;
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

    public String getDocumentStatus(int externalId, String externalTable, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Status Found";
        String description = "User: "+userId+" found doc status";
        String method = "GET";
        int status = 200;

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));
        return documentRepository.callValidateDocumentsStoredProcedure(externalId, externalTable);
    }

    public List<DocumentDto> findAll(String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Found All";
        String description = "User: "+userId+" found all docs";
        String method = "GET";
        int status = 200;

        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> results = new ArrayList<>();
        for(Document d: documentList) {
            DocumentDto dto = new DocumentDto(d);
            String newUrl = obtainPreSignedURLFromDocumentToViewOnly(dto);
            dto.setStorageUrl(newUrl);
            results.add(dto);
        }

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return results;
    }

    public List<DocumentDto> getDocumentsForAutomotiveGroup(int id, String email) throws Exception {
        UserDealershipDto userDto = userClient.findUserByEmail(email);

        int userId = userDto.getId();
        String title = "Document Found All For User";
        String description = "User: "+userId+" got all user docs of AG: "+id;
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
                String newUrl = obtainPreSignedURLFromDocumentToViewOnly(dto);
                dto.setStorageUrl(newUrl);
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
        String description = "User: "+userId+" deleted doc: "+id;
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
        String description = "User: "+userId+" found user docs of user: "+id;
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
                String newUrl = obtainPreSignedURLFromDocumentToViewOnly(dto);
                dto.setStorageUrl(newUrl);
                results.add(dto);
            }
        }

        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return results;
    }

    public List<DocumentDto> getDocumentsFrom(String externalTable, int externalId, String email) throws Exception {
        List<DocumentDto> documentList = documentRepository.findAllDtoByExternalIdAndExternalTable(externalId, externalTable);
        ArrayList<DocumentDto> filteredList = new ArrayList<>();

        if (documentList.isEmpty()) {
            throw new Exception("No documents found with type: " + externalTable + " and with id: " + externalId);
        }

        for (DocumentDto document : documentList) {
            if(!document.isDeleted()) {
                String newUrl = obtainPreSignedURLFromDocumentToViewOnly(document);
                document.setStorageUrl(newUrl);
                filteredList.add(document);
            }
        }

        int userId = userClient.findUserByEmail(email).getId();
        String title = "Get Documents from";
        String description = "User: "+userId+" req docs from "+externalTable+" -> "+externalId;
        String method = "GET";
        int status = 200;
        logService.saveLog(LogFactory.createLog(userId,title,description,method,status));

        return filteredList;
    }

    public int findDocumentIdByUrl(String url) {
        Document document = documentRepository.findByStorageUrl(url);
        if (document != null) {
            return document.getDocumentId();
        } else {
            return -1;
        }
    }

    public int findDocumentByExternalTableIdAndReqDocId (String externalTable, int externalId, int reqDocId) {
        Optional<DocumentRequired> docReqOptional = documentRequiredRepository.findById(reqDocId);
        Document document = documentRepository.findDocumentByExternalTableAndExternalIdAndDocumentRequiredId(externalTable, externalId, docReqOptional.get());
        if (document != null) {
            return document.getDocumentId();
        } else {
            return -1;
        }
    }

    public String obtainPreSignedURLFromDocumentToViewOnly (DocumentDto document) throws Exception {
        URL url = new URL(document.getStorageUrl());
        String fileName = url.getPath().substring(1);
        return awsS3Service.getPreSignedURL(fileName, "drive-ai-ccm", HttpMethod.GET);
    }

    public String obtainPreSignedURLFromDocumentToViewOnly(Document document) throws Exception {
        URL url = new URL(document.getStorageUrl());
        String fileName = url.getPath().substring(1);
        return awsS3Service.getPreSignedURL(fileName, "drive-ai-ccm", HttpMethod.GET);
    }
}
