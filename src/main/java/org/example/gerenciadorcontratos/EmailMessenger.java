package org.example.gerenciadorcontratos;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailMessenger {
    public static void sendEmail(String host, String port, final String email, final String password, String toAddress,
                                 String subject, String message) throws AddressException, MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        Session session = Session.getInstance(properties, auth);
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(email));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new java.util.Date());
        msg.setText(message);

        Transport.send(msg);
    }

}
