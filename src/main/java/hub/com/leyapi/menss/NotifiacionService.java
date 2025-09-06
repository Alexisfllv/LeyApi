package hub.com.leyapi.menss;

import org.springframework.core.io.Resource;

import java.io.File;

public interface NotifiacionService {

    // para correo
    void notifyUsuario(String to , String subject, String body);

    // para correo con pdf
    void notifyUsuarioWithAttachment(String to , String subject , String body , Resource resource);
}
