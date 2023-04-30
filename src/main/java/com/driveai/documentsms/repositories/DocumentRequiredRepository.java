package com.driveai.documentsms.repositories;

import com.driveai.documentsms.models.DocumentRequired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DocumentRequiredRepository extends CrudRepository {
    List<DocumentRequired> findAll();
}
