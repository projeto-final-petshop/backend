package br.com.finalproject.petconnect.user.services.constants;

public class UserUtilsConstants {

    // Error Messages
    public static final String EMAIL_NOT_NULL = "email.notNull";
    public static final String CPF_NOT_NULL = "cpf.notNull";
    public static final String EMAIL_NOT_FOUND = "email.notFound";
    public static final String CPF_NOT_FOUND = "cpf.notFound";
    public static final String EMAIL_ALREADY_EXISTS = "email.alreadyExists";
    public static final String CPF_ALREADY_EXISTS = "cpf.alreadyExists";
    public static final String PASSWORD_MISMATCH = "password.mismatch";
    public static final String NAME_NOT_NULL = "name.notNull";
    public static final String PASSWORD_NOT_NULL = "password.notNull";
    public static final String INVALID_FIELD = "invalid.field";
    public static final String INVALID_EMAIL = "invalid.email";
    public static final String INVALID_CPF = "invalid.cpf";
    public static final String INVALID_EMAIL_FORMAT = "invalid.email.format";
    public static final String INVALID_CPF_FORMAT = "invalid.cpf.format";

    // Regular Expressions
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String CPF_REGEX = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";


}
