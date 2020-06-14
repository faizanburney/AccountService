package com.fburney.task.repo;

import com.fburney.task.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,String> {
}
