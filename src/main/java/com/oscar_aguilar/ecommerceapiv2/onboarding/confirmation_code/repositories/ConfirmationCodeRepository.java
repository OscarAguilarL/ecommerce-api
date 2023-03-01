package com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.repositories;

import com.oscar_aguilar.ecommerceapiv2.onboarding.confirmation_code.entities.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, UUID> {

    Optional<ConfirmationCode> findByCode(Integer code);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationCode c SET c.confirmedAt = ?2 WHERE c.code = ?1")
    void updateConfirmedAt(Integer code, LocalDateTime confirmedAt);
}
