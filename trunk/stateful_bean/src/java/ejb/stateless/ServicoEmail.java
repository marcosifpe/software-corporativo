package ejb.stateless;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author MASC
 */
@Stateless
public class ServicoEmail implements ServicoEmailLocal {
    @Resource(name = "mail/servicoEmailSession")
    private Session sessao;
    @Resource(name = "mail/servicoEmailProperties")
    private Properties propriedades;

    @Override
    @Asynchronous
    public void enviarMensagem(String para) {
        try {
            Message message = new MimeMessage(sessao);
            message.setFrom(new InternetAddress(propriedades.getProperty("de")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(para));
            message.setSubject(propriedades.getProperty("assunto"));
            message.setText(propriedades.getProperty("mensagem"));
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(ServicoEmail.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
}
