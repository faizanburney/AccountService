package com.fburney.task.repo;

import com.fburney.task.model.SecurityData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface SecurityRepo extends MongoRepository<SecurityData,String> {
    @Aggregation("{ '$group' : { '_id' : { '$concat': ['$name', '$symbol'] }," +
            "'name' : { '$first' : '$name' }," +
            "'symbol' : { '$first' : '$symbol' } } }")
    List<SecurityData> findDistinctSecurities();

    SecurityData findFirstByOrderByStartTimeDesc();

    List<SecurityData> findBySymbolOrderByStartTimeDesc(String symbol, Pageable pageable);
}

