package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.UsuarioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoEmail;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;


    private final JavaMailSender emailSender;




    public void sendEmail(UsuarioRequestDTO dto, TipoEmail tipoEmail) throws Exception {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(dto.getEmail());
            mimeMessageHelper.setSubject(tipoEmail.toString() + " do usuario "+ dto.getNome()+ " na Natureza Conectada ");
            mimeMessageHelper.setText(geContentFromTemplate(dto,tipoEmail), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public String geContentFromTemplate(UsuarioRequestDTO dto,TipoEmail tipoEmail) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", dto.getNome());
        dados.put("mensagem", tipoEmail.getMensagem());

        Template template = fmConfiguration.getTemplate("email-usuario-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}
