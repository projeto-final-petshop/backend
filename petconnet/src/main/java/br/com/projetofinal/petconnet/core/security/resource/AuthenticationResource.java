package br.com.projetofinal.petconnet.core.security.resource;

import br.com.projetofinal.petconnet.core.security.jwt.JwtDto;
import br.com.projetofinal.petconnet.core.security.jwt.JwtProvider;
import br.com.projetofinal.petconnet.core.security.service.CustomUserDetails;
import br.com.projetofinal.petconnet.core.security.service.CustomUserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationResource {

    private JwtProvider jwtProvider;

    private final CustomUserDetailsServiceImpl userDetailsService;

    public AuthenticationResource(JwtProvider jwtProvider, CustomUserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public ResponseEntity<JwtDto> authenticationRequest(@RequestBody JwtDto login) {
        Authentication authentication = null;
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(login.getPassword(), userDetails.getUser().getPassword())) {
            authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
        } else {
            return ResponseEntity.badRequest().body(null);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtProvider.generateJwt(userDetails);
        String roles = "";

        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken(jwt);
        jwtDto.setUsername(login.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(jwtDto);

    }
}
