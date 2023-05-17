package com.driveai.documentsms.repositories;

import com.driveai.documentsms.models.Log;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface LogRepository extends CrudRepository<Log,Integer> {
    List<Log> findAll();
}
