package com.jap.microservice.accountservice.db.repository;

import com.jap.microservice.accountservice.db.entity.Account;
import feign.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getFirstByEmail(String email);
    Account getFirstByPassword(String password);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="10000")})
    Optional<Account> findWithLockingById(Long id);

    @Query(value = "select * "
            + "from Account t "
            + "where t.id = :id", nativeQuery = true)
    AtomicReference<Account> getData(Long id);


    @Modifying
    @Query(value = "UPDATE Account t "
            + "SET t.amount = :amount "
            + "WHERE t.id = :id")
    void updateAmount(@Param("amount")int amount,@Param("id") Long id);

}
