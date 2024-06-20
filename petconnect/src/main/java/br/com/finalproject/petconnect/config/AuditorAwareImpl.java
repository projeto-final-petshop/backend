package br.com.finalproject.petconnect.config;

import br.com.finalproject.petconnect.user.entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {

    // Not annotated method overrides method annotated with @NonNullApi
    @Override
    public Optional<User> getCurrentAuditor() {
        // Implement the logic to get the current user from the security context or session
        User user = new User();
        user.setId(1L);
        user.setName("admin");
        return Optional.of(user);
    }

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new AuditorAwareImpl();
    }

}
