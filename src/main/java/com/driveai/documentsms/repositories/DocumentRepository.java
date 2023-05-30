package com.driveai.documentsms.repositories;


import com.driveai.documentsms.dto.DocumentDto;
import com.driveai.documentsms.models.Document;
import com.driveai.documentsms.models.DocumentRequired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document,Integer> {
    List<Document> findAll();
    @Query("SELECT new com.driveai.documentsms.dto.DocumentDto(d) FROM Document d " +
            "WHERE d.externalId = :externalId AND d.externalTable = :externalTable AND d.isDeleted = false")
    List<DocumentDto> findAllDtoByExternalIdAndExternalTable(int externalId, String externalTable);
    @Procedure(procedureName = "validate_documents")
    String callValidateDocumentsStoredProcedure(int externalId, String externalTable);

    Document findByStorageUrl(String storageUrl);

    Document findDocumentByExternalTableAndExternalIdAndDocumentRequiredId(String externalTable, int externalId, DocumentRequired reqDocId);
}
