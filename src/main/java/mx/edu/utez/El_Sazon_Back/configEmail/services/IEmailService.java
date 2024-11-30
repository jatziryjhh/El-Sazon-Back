package mx.edu.utez.El_Sazon_Back.configEmail.services;

import jakarta.mail.MessagingException;
import mx.edu.utez.El_Sazon_Back.configEmail.services.models.EmailDto;

public interface IEmailService {
    public void sendMail(EmailDto emailDto) throws MessagingException;
}
