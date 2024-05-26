package br.com.finalproject.petconnect.password;

import br.com.finalproject.petconnect.exceptions.runtimes.PasswordUpdateException;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PasswordService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) throws PasswordUpdateException {

        User currentUser = getCurrentAuthenticatedUser();

        if (!passwordEncoder.matches(passwordUpdateRequest.getCurrentPassword(), currentUser.getPassword())) {
            throw new PasswordUpdateException("A senha atual está incorreta.");
        }

        if (!passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmPassword())) {
            throw new PasswordUpdateException("A nova senha e a confirmação da senha não coincidem.");
        }

        currentUser.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(currentUser);

    }

    private User getCurrentAuthenticatedUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

}
