package com.example.BloodBank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("isa.banka.tim.62@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");


    }

    @Async
    public void sendMailWithAttachment(String to, String subject, String body, Map<String, DataSource> files) throws Exception
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("isa.banka.tim.62@gmail.com"));
                mimeMessage.setSubject(subject);

                Multipart multipart = new MimeMultipart();

                MimeBodyPart textBp = new MimeBodyPart();
                textBp.setContent(body,"text/html; charset=utf-8");

                ArrayList<MimeBodyPart> attachments = new ArrayList<>();
                for (Map.Entry<String, DataSource> file: files.entrySet()) {
                    MimeBodyPart mbp = new MimeBodyPart();
                    mbp.setDataHandler(new DataHandler(file.getValue()));
                    mbp.setFileName(file.getKey());
                    attachments.add(mbp);
                }

                multipart.addBodyPart(textBp);
                for (MimeBodyPart part: attachments) {
                    multipart.addBodyPart(part);
                }

                mimeMessage.setContent(multipart);
            }
        };
        mailSender.send(preparator);
    }

    @Async
    public void sendMailWithInlineResources(String to, String subject, String body, Map<String, DataSource> files)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("isa.banka.tim.62@gmail.com"));
                mimeMessage.setSubject(subject);

                Multipart multipart = new MimeMultipart();

                String emailText = "<html><body>" + body + "<br/>";

                ArrayList<MimeBodyPart> inlines = new ArrayList<>();
                for (Map.Entry<String, DataSource> file: files.entrySet()) {
                    MimeBodyPart mbp = new MimeBodyPart();
                    mbp.setDataHandler(new DataHandler(file.getValue()));
                    mbp.setHeader("Content-ID", "<" + file.getKey() + ">");
                    inlines.add(mbp);
                    emailText = emailText + "<img src='cid:" + file.getKey() +  "'><br/>";
                }

                MimeBodyPart textBp = new MimeBodyPart();
                textBp.setContent(emailText,"text/html; charset=utf-8");

                multipart.addBodyPart(textBp);
                for (MimeBodyPart part: inlines) {
                    multipart.addBodyPart(part);
                }
                mimeMessage.setContent(multipart);
            }
        };
        mailSender.send(preparator);
    }

}
