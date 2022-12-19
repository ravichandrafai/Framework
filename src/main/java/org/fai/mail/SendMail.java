package org.fai.mail;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.fai.enums.PropertyEnums;
import org.fai.reports.FrameworkLogger;
import org.fai.utils.DecodeUtils;
import org.fai.utils.ReadProperties;

public class SendMail {

     public static void sendEmail(){

    	Session session;
    	FrameworkLogger.logInfo("Start of send mail");
        String host = ReadProperties.get(PropertyEnums.SMTPHOST);
        String port = ReadProperties.get(PropertyEnums.SMTPPORT);
        String sender = ReadProperties.get(PropertyEnums.SENDER);
        String encryptedpass=ReadProperties.get(PropertyEnums.SENDERPWD);
        String subject = ReadProperties.get(PropertyEnums.SUBJECT);
        String mailBody = ReadProperties.get(PropertyEnums.MAILTEXT);
        String recipients = ReadProperties.get(PropertyEnums.RECIPIENTS);
        String filePath = ReadProperties.get(PropertyEnums.REPORTLOCATION);
        String decryptedPass= DecodeUtils.getDecodedString(encryptedpass);
        String      auth="tls";
        boolean     doAuth = !"none".equals(auth);
        boolean     useTLS = "tls".equals(auth);

        String[] to = recipients.split(",");
        InternetAddress[] address = new InternetAddress[to.length];
   
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
    
        if (useTLS) {
        	properties.put("mail.smtp.socketFactory.port",     port);
        	properties.put("mail.smtp.socketFactory.class",    "javax.net.ssl.SSLSocketFactory");
        	properties.put("mail.smtp.socketFactory.fallback", "false");
        	properties.put("mail.smtp.starttls.enable",        "true");
        	properties.put("mail.smtp.ssl",                    "true");
          }

          if (doAuth) {
          
            properties.put("mail.user",      sender);
            properties.put("mail.smtp.auth", "true");

             session = Session.getInstance(properties, new javax.mail.Authenticator() {
            	 
                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(sender, decryptedPass);
    
                }

            });
          }
          else 
            session = Session.getInstance(properties);

        // Used to debug SMTP issues
        //session.setDebug(true);

        try {
            // Create a default MimeMessage object.
        	
            MimeMessage message = new MimeMessage(session);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

           for (int i = 0; i < to.length; i++) {
                address[i] = new InternetAddress(to[i]);
            }
            	message.setFrom(new InternetAddress(sender));
                message.addRecipients(Message.RecipientType.TO, address);
                message.setSubject(subject);
                message.setText(mailBody);
                message.setContent(multipart);
                textPart.setText(mailBody);
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            try {

                File f =new File(filePath);
                attachmentPart.attachFile(f);
            

            } catch (IOException e) {

                e.printStackTrace();

            }
            Transport.send(message);
          
        } catch (MessagingException mex) {
        	
            mex.printStackTrace();
        
        }
        FrameworkLogger.logInfo("End of mail");
    }
     
}