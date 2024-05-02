package br.com.projetofinal.petconnet.core.security.filter;

import br.com.projetofinal.petconnet.core.exceptions.errors.users.InvalidCredentialException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Classe de filtro para validação de requisições antes do processamento.
 * <p>
 * Essa classe implementa a interface {@link Filter} do pacote jakarta.servlet.
 *
 * @author juliane.maran
 */
public class RequestValidationBeforeFilter implements Filter {

    /**
     * Constante que representa o esquema de autenticação básica.
     */
    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";

    /**
     * Constante que define o charset utilizado para decodificar as credenciais.
     */
    private static final Charset CREDENTIALS_CHARSET = StandardCharsets.UTF_8;

    /**
     * Sobrescreve o método {@code doFilter} da interface {@link Filter}.
     * <p>
     * Esse método é chamado antes do processamento da requisição pelo servlet.
     * <p>
     * Esse método intercepta a requisição ({@link ServletRequest}) e a resposta ({@link ServletResponse}) antes do
     * processamento pela cadeia de filtros ({@link FilterChain}).
     *
     * @param request
     *         A requisição HTTP. {@link ServletRequest})
     * @param response
     *         A resposta HTTP. ({@link ServletResponse})
     * @param chain
     *         A cadeia de filtros ({@link FilterChain}) para processamento posterior.
     *
     * @throws IOException
     *         Caso ocorra uma exceção de entrada/saída.
     * @throws ServletException
     *         Caso ocorra uma exceção relacionada ao servlet.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String header = httpServletRequest.getHeader(AUTHORIZATION);

        if (header != null) {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                handleBasicAuthentication(header, httpServletResponse);
            }
        }

    }

    /**
     * Realiza a validação da autenticação básica.
     * <p>
     * Esse método extrai o token base64 do cabeçalho, decodifica-o e verifica se o email do usuário contém a string
     * "test" (caso de teste, modifique a lógica de validação).
     * <p>
     * header: String que contém o cabeçalho de autenticação básica recebido da requisição.
     * <p>
     * res: Um objeto {@link HttpServletResponse} que permite definir o código de status da resposta.
     *
     * <p>byte[] base64Token = header.substring(6).getBytes(CREDENTIALS_CHARSET);</p>
     *
     * @param header
     *         O cabeçalho (header) de autorização contendo o token base64.
     * @param res
     *         A resposta HTTP ({@link HttpServletResponse}).
     *
     * @throws InvalidCredentialException
     *         Caso a autenticação básica seja inválida.
     */
    private void handleBasicAuthentication(String header, HttpServletResponse res) {

        // Esta linha extrai o token de autenticação básica do cabeçalho recebido.
        // header.substring(6): Pega uma substring do cabeçalho, começando do sétimo caractere (índice 6).
        // Isso porque o cabeçalho de autenticação básica geralmente segue o formato Authorization:
        // Basic base64Token, onde base64Token é o token codificado em Base64.
        // .getBytes(CREDENTIALS_CHARSET): Converte a substring do cabeçalho para um array de bytes usando o charset
        // definido por CREDENTIALS_CHARSET (provavelmente UTF-8).
        byte[] base64Token = header.substring(6).getBytes(CREDENTIALS_CHARSET);

        // Esta linha declara uma variável decoded do tipo byte[] para armazenar o token decodificado.
        byte[] decoded;

        // Este bloco try-catch tenta decodificar o token Base64 e verifica se ele está no formato esperado.
        try {

            // Decodifica o token Base64 armazenado em base64Token e armazena o resultado decodificado em decoded.
            decoded = Base64.getDecoder().decode(base64Token);
            //  Converte o array de bytes decodificado (decoded) de volta para uma String usando o charset definido
            //  por CREDENTIALS_CHARSET.
            String token = new String(decoded, CREDENTIALS_CHARSET);

            // Procura o caractere ":" (dois pontos) dentro da string token.
            int delim = token.indexOf(":");
            // Verifica se o caractere ":" foi encontrado.
            if (delim == -1) {
                // Se não for encontrado (delim == -1), significa que o token não está no formato esperado
                // (e-mail:senha) e uma exceção InvalidCredentialException é lançada.
                throw new InvalidCredentialException("Token de autenticação básica inválido.");
            }

            // Extrai a substring do token antes do caractere ":" e armazena em email.
            String email = token.substring(0, delim);
            //  Converte o e-mail para letras minúsculas e verifica se ele contém a substring "petconnect".
            if (email.toLowerCase().contains("petconnect")) {
                // Se contiver "test", o código de status da resposta é definido como
                // SC_BAD_REQUEST (400 - Requisição Inválida).
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (IllegalArgumentException ex) {
            //  Se ocorrer uma exceção do tipo IllegalArgumentException durante a decodificação do Base64, ela é
            //  capturada e uma exceção InvalidCredentialException é lançada com uma mensagem informando que a
            //  decodificação falhou.
            throw new InvalidCredentialException("Falha ao decodificar o token de autenticação básico");
        }

    }

}
