

package services;





import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public SendMail() {
    }

    public static void sendEmail(String recipientEmail, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        String username = "batahapp@gmail.com";
        String password = "gpay ypxn mcnf uiod";

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

            System.out.println("Message envoyé avec succès à : " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi du message : " + e.getMessage());
        }
    }

    public static void main(String[] args) {




        // Exemple : Création d'un objet user avec une adresse e-mail
        SendMail s = new SendMail() ;

        s.sendEmail("werghiaicha73@gmail.com","welcome","Dear [Client's Name],\n" +
                "\n" +
                "Welcome to IMPACTFUL JOURNYES ! We are thrilled to have you as a valued member of our travel community.\n" +
                "\n" +
                "At IMPACTFUL JOURNYES , our mission is to create unforgettable travel experiences for our clients. " +
                "Whether you're planning a relaxing beach getaway, an adventurous trek through the mountains," +
                " or a cultural exploration of a vibrant city, we are here to turn your travel dreams into reality.");

    }



}