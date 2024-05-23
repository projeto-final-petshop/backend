package br.com.project.petconnect.core.security.dto;

import jakarta.validation.constraints.Email;

public record LoginRequest(@Email String username, String password) {
}
