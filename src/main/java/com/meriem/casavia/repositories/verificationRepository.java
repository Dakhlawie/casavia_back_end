package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.verification;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface verificationRepository extends JpaRepository<verification,Long> {
    verification findByEmail(String email);
}
