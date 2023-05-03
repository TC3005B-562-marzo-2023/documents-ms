package com.driveai.documentsms.services;

import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.models.DocumentRequired;
import com.driveai.documentsms.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /*public DocumentDto findDocumentById(int documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("El documento con el id " + documentId + " no se encuentra."));
        return documentMapper.toDocumentDto(document);
    }*/

    public Document findDocumentById(int documentId) throws Exception {
        Optional<Document> documentInDB = documentRepository.findById(documentId);
        if (!documentInDB.isPresent()) { //Check if documentRequired exists
            throw new Exception("Unable to find document with id: "+ documentId);
        }
        Document document = documentInDB.get();
        return document; //Save document object
    }

}
