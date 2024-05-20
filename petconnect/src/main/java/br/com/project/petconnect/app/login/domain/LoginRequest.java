package br.com.project.petconnect.app.login.domain;

import jakarta.validation.constraints.Email;

public record LoginRequest(@Email String email, String password) {
}
