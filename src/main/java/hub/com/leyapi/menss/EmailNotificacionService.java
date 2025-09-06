package hub.com.leyapi.menss;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailNotificacionService implements NotifiacionService {

    // Inyeccion para servicio de Spring para correos
    private final JavaMailSender mailSender;

    @Override
    public void notifyUsuario(String to, String subject, String body) {
        // Crear mensaje simple de correo
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        // Enviar correo
        mailSender.send(message);
    }

    @Override
    public void notifyUsuarioWithAttachment(String to, String subject, String body, Resource resource) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,false);

            // adjuntar pdf
            helper.addAttachment(resource.getFilename(), resource);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error enviado correo con adjunto " +e);
        }
    }
}
