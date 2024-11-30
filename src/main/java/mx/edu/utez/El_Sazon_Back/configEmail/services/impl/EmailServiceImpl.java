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
    public void sendMail(EmailDto emailDto) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailDto.getDestinatario());
            helper.setSubject(emailDto.getAsunto());

            Context context = new Context();
            context.setVariable("message", emailDto.getMensaje());
            String contentHTML = templateEngine.process("email", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Error al enviar el correo electrónico" + e.getMessage(), e);
        }
    }
}
