package br.com.projetofinal.petconnet.core.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

    CustomUserDetails loadUserByUsername(String username);

}
