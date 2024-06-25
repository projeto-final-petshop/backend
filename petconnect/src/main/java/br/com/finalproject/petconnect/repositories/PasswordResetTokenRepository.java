package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<ResetToken, Long> {

    Optional<ResetToken> findByToken(String token);

}
