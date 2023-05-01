package com.driveai.documentsms.repositories;

import com.driveai.documentsms.models.DocumentRequired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DocumentRequiredRepository extends CrudRepository<DocumentRequired,Integer> {
    List<DocumentRequired> findAll();
}
