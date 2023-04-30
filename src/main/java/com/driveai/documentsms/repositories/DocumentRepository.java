package com.driveai.documentsms.repositories;

import com.driveai.documentsms.models.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DocumentRepository extends CrudRepository {
    List<Document> findAll();
}
