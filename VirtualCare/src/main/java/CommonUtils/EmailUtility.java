/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package CommonUtils;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import model.UserDetails;



/**
*
* @author akshay 
*
*/
public class EmailUtility {

public void sendMail(UserDetails user) {
    String sender = "10sionless@gmail.com";
    String senderEmailPass = "123@Akshay";
    String receiverEmail = user.getEmail();
    
    Properties prop = System.getProperties();
    String host = "smtp.gmail.com";
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.ssl.trust", host);
    prop.put("mail.smtp.user", sender);
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    Session session = Session.getDefaultInstance(prop);
    try {
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(sender));
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
    message.setSubject("Registration Successful");
    if(user.isRegistrationForm()){
    message.setText("Dear "+user.getFirstName()+", "
    + "\n\n Thank you for registering "
    + "\n\n Please find your login details- "
    + "\n\n EmailId: "+receiverEmail+""
    + "\n Password: "+user.getPassword()+""
    + "\n\n\n Regards, "
    + "\n Virtual Care");
    }else{
         message.setText("Dear "+user.getFirstName()+", "
        + "\n\n Please find below link to join virtually"
        + "\n\n Zoom Link:"
       + "\n https://zoom.us/j/99792220260?pwd=UXY0cS93aC9XTzJWUGVWSzA4M29Idz09   "
        + "\n\n\n Regards, "
        + "\n Virtual Care");
    }
    Transport transport = session.getTransport("smtp");
    transport.connect(host, sender, senderEmailPass);
    transport.sendMessage(message, message.getAllRecipients());
    transport.close();

    } catch (MessagingException ex) {
    JOptionPane.showMessageDialog(null, "Please enter valid email");
    }
    }
}