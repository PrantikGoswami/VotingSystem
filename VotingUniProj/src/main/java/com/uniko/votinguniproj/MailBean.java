/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uniko.votinguniproj;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author usman
 */

public class MailBean {
    private static final Logger LOG = Logger.getLogger(MailBean.class.getName());
    
    @Resource(name="mail/javamail")
    private Session session;
    
    public String send(String subject, String text, String to)
    {
        //LOG.log(Level.INFO, "MailBean : logged in user:Subject {0}", subject);
        //LOG.log(Level.INFO, "MailBean : logged in user:Text {0}", text);
        //LOG.log(Level.INFO, "MailBean : logged in user:To {0}", to);
        String status = "Failed";
        Message msg = new MimeMessage(session);
        try {
            msg.setSubject(subject);
            msg.setText(text);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            Transport.send(msg);
            status = "Success";
        
        } catch (MessagingException ex) {
            System.out.println("Email not sent.");
            ex.printStackTrace();
        }
        return status;
    }
}
