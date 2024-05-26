package br.com.finalproject.petconnect.exceptions.dto;

/**
 * @param title
 *         "Unauthorized"
 * @param status
 *         401
 * @param details
 *         "Bad credentials"
 * @param message
 *         "The username or password is incorrect"
 */
public record ErrorResponse(String title, int status, String details, String message) {


}
