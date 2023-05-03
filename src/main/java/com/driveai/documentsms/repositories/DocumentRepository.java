package com.driveai.documentsms.repositories;


import com.driveai.documentsms.models.Document;
import feign.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document,Integer> {
    List<Document> findAll();
    @Procedure(procedureName = "validate_documents")
    String callValidateDocumentsStoredProcedure(int externalId, String externalTable);

}
