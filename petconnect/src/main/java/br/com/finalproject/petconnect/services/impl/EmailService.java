package br.com.finalproject.petconnect.services.impl;

import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

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
            throw new ServiceException();
        }
    }

}
