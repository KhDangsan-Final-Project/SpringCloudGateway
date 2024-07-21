package com.scg.repository;

import com.scg.model.RequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestLogRepository extends MongoRepository<RequestLog, String> {
}
