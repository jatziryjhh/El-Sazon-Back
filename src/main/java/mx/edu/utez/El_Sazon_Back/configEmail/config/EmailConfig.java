package mx.edu.utez.El_Sazon_Back.configEmail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${email.username}")
    private String email;

    @Value("${email.password}")
    private String password;

    private Properties getMailProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender= new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setUsername(email);
        javaMailSender.setPassword(password);
        return javaMailSender;
    }

    @Bean
    public ResourceLoader resourceLoader(){
        return new DefaultResourceLoader();
    }
}
