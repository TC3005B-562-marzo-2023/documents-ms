package com.driveai.documentsms.repositories;


import com.driveai.documentsms.models.Document;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document,Integer> {
    List<Document> findAll();
    @Procedure(procedureName = "validate_documents")
    String callValidateDocumentsStoredProcedure(int externalId, String externalTable);

    Document findByStorageUrl(String storageUrl);
}
