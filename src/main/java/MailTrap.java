import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class MailTrap implements ImessageMail, IAttachmentMail {
    private String sender;
    private String recipients;
    private String subject;
    private Session session;
    private ArrayList<MimeBodyPart> mimeBodyParts;


    public MailTrap(String username, String password) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "2525");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        this.session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        mimeBodyParts = new ArrayList<>();
    }

    @Override
    public void addMessage(String s) {
        try {
            MimeBodyPart tmp = new MimeBodyPart();
            tmp.setContent(s, "text/plain; charset=UTF-8");
            mimeBodyParts.add(tmp);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAttachment(File file) {
        MimeBodyPart tmp = new MimeBodyPart();
        try {
            tmp.attachFile(file);
            mimeBodyParts.add(tmp);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMail() {
        try {
            Multipart multipart = new MimeMultipart();
            for (MimeBodyPart mbp : mimeBodyParts) {
                multipart.addBodyPart(mbp);
            }

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(recipients));
            message.setSubject(subject);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<MimeBodyPart> getMimeBodyParts() {
        return mimeBodyParts;
    }
}
