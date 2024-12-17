package br.com.microservices.microservices.sendemail.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.sendemail.models.ContatoModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailService;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailToClientNewCliente(String token, String emailUsuario) {
        try {
            var contatoModel = new ContatoModel().generateHtmlEmail(token, emailUsuario);
            String title = "Confirmação de Conta";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailService);
            helper.setTo(emailUsuario);
            helper.setSubject(title);
            helper.setText(contatoModel, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmailToReview(ContatoModel contato) {
        try {
            var contatoModel = new ContatoModel();
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            if (contato.getReview() > 1 || contato.getReview() <= 5) {
                if (!contatoModel.validarEmail(contato.getRemetente())) {
                    contato.setRemetente(emailService);
                }
                helper.setTo(contato.getRemetente());
                helper.setFrom(emailService);
                helper.setSubject(contato.getTitulo());
                var contatoModelBody = contatoModel.generateHtmlForReview(contato.getTitulo(), contato.getReview(),
                        contato.getBody());
                helper.setText(contatoModelBody, true);
                javaMailSender.send(message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar o e-mail para " +
                    contato.getDestinatario());
        }
    }
}
