package br.com.finalproject.petconnect.email;

import br.com.finalproject.petconnect.exceptions.runtimes.email.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Transactional
    public void sendEmail(String to, String subject, String text) {
        try {
            var message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            log.info("E-mail enviado para: {}", to);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail para: {}", to, e);
            throw new EmailSendException();
        }
    }

    @Transactional
    public void sendResetPasswordEmail(String to, String name, String link) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Reset de Senha");

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("message", "Você solicitou a redefinição de senha. Para continuar o processo, clique no link abaixo:");
            context.setVariable("link", link);

            String htmlContent = templateEngine.process("reset-password-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("E-mail enviado para: {}", to);
        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail para: {}", to, e);
            throw new EmailSendException();
        }
    }

}
