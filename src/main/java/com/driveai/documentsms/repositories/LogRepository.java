package com.driveai.documentsms.repositories;

import com.driveai.documentsms.models.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface LogRepository extends CrudRepository<Log,Integer> {
    List<Log> findAll();
}
