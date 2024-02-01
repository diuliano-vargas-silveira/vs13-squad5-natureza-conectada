package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.UsuarioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.EntregaResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoEmail;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
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

    public String getContentFromTemplate( TipoEmail tipoEmail,EntregaResponseDTO dto) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", dto.getCliente().getNome());
        dados.put("email",dto.getCliente().getEmail());
        dados.put("mensagem", tipoEmail.getMensagemEntrega().concat(String.valueOf(dto.getId())));

        Template template = fmConfiguration.getTemplate("email-entrega-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
    public void sendEmail(EntregaResponseDTO dto, TipoEmail tipoEmail) throws Exception {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(dto.getCliente().getEmail());
            mimeMessageHelper.setSubject(tipoEmail.toString() + " da entrega com Id: "+dto.getId()+" do usuario "+ dto.getCliente().getNome()+ " na Natureza Conectada ");
            mimeMessageHelper.setText(getContentFromTemplate(tipoEmail,dto), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("Erro no email ");
        }
    }

    public String geContentFromTemplate(UsuarioRequestDTO dto,TipoEmail tipoEmail) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", dto.getNome());
        dados.put("email",dto.getEmail());
        dados.put("mensagem", tipoEmail.getMensagemUsuario());

        Template template = fmConfiguration.getTemplate("email-usuario-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}
