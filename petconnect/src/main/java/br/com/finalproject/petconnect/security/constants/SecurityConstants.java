package br.com.finalproject.petconnect.security.constants;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class SecurityConstants {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String BEARER = "Bearer ";

    public static final String ACCESS_CONTROL_ALLOWED_ORIGINS = "http://localhost:4200";
    public static final String ACCESS_CONTROL_ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    public static final String ACCESS_CONTROL_ALLOWED_HEADERS = "Authorization, Content-Type";

    public static final Marker JWT_AUTH_FILTER_MARKER = MarkerFactory.getMarker("AUTH"); // JwtAuthenticationFilter
    public static final Marker JWT_SERVICE_MARKER = MarkerFactory.getMarker("JWT"); // JwtService

}
