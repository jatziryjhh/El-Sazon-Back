package mx.edu.utez.El_Sazon_Back.configEmail.controllers;

import jakarta.mail.MessagingException;
import mx.edu.utez.El_Sazon_Back.configEmail.services.IEmailService;
import mx.edu.utez.El_Sazon_Back.configEmail.services.models.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enviar-correo")
public class EmailController {

    @Autowired
    IEmailService iEmailService;

    @PostMapping("/")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto email) throws MessagingException {
        iEmailService.sendMail(email);
        return new ResponseEntity<>("Correo enviado exitosamente", HttpStatus.OK);
    }

}
