package br.com.finalproject.petconnect.security.services;


import br.com.finalproject.petconnect.security.configs.ApplicationConfiguration;
import br.com.finalproject.petconnect.security.dto.LoginRequest;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test") // Usar perfil de teste, se necessário
@Import({ApplicationConfiguration.class})
class AuthenticationServiceTest {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationService authenticationService;

    @Test
    void testAuthenticate() {
        // Adicionando logs para depuração
        System.out.println("Iniciando o teste de autenticação...");

        // Crie um usuário com senha codificada
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("P4$$w0rD"));
        userRepository.save(user);

        System.out.println("Usuário salvo: " + user.getEmail());

        // Teste a autenticação
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("P4$$w0rD");

        User authenticatedUser = authenticationService.authenticate(loginRequest);

        // Logs adicionais
        System.out.println("Usuário autenticado: " + (authenticatedUser != null ? authenticatedUser.getEmail() : "null"));

        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmail());
    }

}