package com.fburney.task.repo;

import com.fburney.task.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepo extends MongoRepository<Account,String> {
}
