package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailModel sendEmail(EmailModel emailModel) {

        emailModel.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage mensage = new SimpleMailMessage();
            mensage.setFrom(emailModel.getEmailFrom());
            mensage.setTo(emailModel.getEmailTo());
            mensage.setSubject(emailModel.getSubject());
            mensage.setText(emailModel.getText());

            javaMailSender.send(mensage);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
