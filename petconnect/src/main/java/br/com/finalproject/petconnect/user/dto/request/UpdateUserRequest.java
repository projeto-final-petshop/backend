package br.com.finalproject.petconnect.user.dto.request;

public record UpdateUserRequest(String name,
                                String phoneNumber,
                                String address) {
}
