package mx.edu.utez.El_Sazon_Back.configEmail.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import mx.edu.utez.El_Sazon_Back.configEmail.services.IEmailService;
import mx.edu.utez.El_Sazon_Back.configEmail.services.models.EmailDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendMail(EmailDto email) throws MessagingException {
        // Crear un MimeMessage para el correo
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // Configurar los detalles del correo
        helper.setTo(email.getDestinatario());
        helper.setSubject(email.getAsunto());
        helper.setText("<html><body><h2 style='color: #000000;'>Te informamos que se ha registrado un nuevo pedido en Cafetería *El Sazón*</h2><p style='color: #000000;'>" + email.getMensaje() + "</p></body></html>", true);

        // Enviar el correo
        javaMailSender.send(mimeMessage);
    }
}
