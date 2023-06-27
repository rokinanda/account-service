package com.jap.microservice.accountservice.db.repository;

import com.jap.microservice.accountservice.db.entity.Account;
import com.jap.microservice.accountservice.db.entity.Mahasiswa;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;


@Repository
@Transactional
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, String> {
}
