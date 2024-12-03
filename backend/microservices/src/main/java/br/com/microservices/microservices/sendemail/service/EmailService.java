package br.com.microservices.microservices.sendemail.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${username.email}")
    private String username;

    @Value("${me.email}")
    private String toMe;

    @Value("${password.email}")
    private String password;

    public void sendEmailToClient(String subject, String body) {
        // Configuração das propriedades
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Autenticação com o servidor SMTP
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // Criando a mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // E-mail de origem
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMe)); // Destinatário:
                                                                                          // e-mail do cliente
            message.setSubject(subject); // Assunto do e-mail

            // Corpo do e-mail incluindo o e-mail do cliente
            String emailContent = body + "\n\nSeu e-mail: " + toMe;
            message.setText(emailContent); // Corpo do e-mail

            // Enviando o e-mail
            Transport.send(message);
            System.out.println("E-mail enviado para " + toMe + " com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar o e-mail para " + toMe);
        }
    }
}
