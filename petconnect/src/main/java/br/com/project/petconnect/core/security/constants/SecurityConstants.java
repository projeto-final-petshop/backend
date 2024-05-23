package br.com.project.petconnect.core.security.constants;

public class SecurityConstants {

    public static final String JWT_KEY = "ywapDGV5iwYd0tcWZCjxvoGmUH2liG8yo3FX4Hyn6rTqZ2+GtOXQCpReeAMOzOrX";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "Authorization";
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 milisegundos = 7200 segundos = 2 horas
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token

    // Base64: eXdhcERHVjVpd1lkMHRjV1pDanh2b0dtVUgybGlHOHlvM0ZYNEh5bjZyVHFaMitHdE9YUUNwUmVlQU1Pek9yWA==
    // Encryption Key: ywapDGV5iwYd0tcWZCjxvoGmUH2liG8yo3FX4Hyn6rTqZ2+GtOXQCpReeAMOzOrX

}
