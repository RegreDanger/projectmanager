package domain.services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import domain.User;

public class EmailService {
    private User user;
    private String token;
	
    public EmailService(User user, String token) {
    	this.user = user;
    	this.token = token;
    }

    public void sendVerificationEmail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user.getUsername(), user.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user.getUsername()));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Verificación de correo electrónico");
            message.setText("Por favor, usa el siguiente código para verificar tu cuenta: " + token);
            
            Transport.send(message);

            System.out.println("Correo de verificación enviado a " + user.getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

