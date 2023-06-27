package com.jap.microservice.accountservice.db.repository;

import com.jap.microservice.accountservice.db.entity.TempAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempAccountRepository extends CrudRepository<TempAccount, String> {
    TempAccount getFirstByEmail(String email);
}
