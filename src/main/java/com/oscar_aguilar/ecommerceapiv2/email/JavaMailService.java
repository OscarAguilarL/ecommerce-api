package com.oscar_aguilar.ecommerceapiv2.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService implements EmailService {

    private final static Logger logger = LoggerFactory.getLogger(JavaMailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public JavaMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendHtmlEmail(EmailDetails emailDetails) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(emailDetails.getTo());
            helper.setFrom(this.sender);
            helper.setSubject(emailDetails.getSubject());
            helper.setText(emailDetails.getHtmlTemplate(), true);

            javaMailSender.send(mimeMessage);
            logger.info("Email sent successfully");
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void sendHtmlEmailWithAttachments(EmailDetails emailDetails) {
//        TODO: Implement send email with attachments
        logger.info("Email with attachment sent");
    }
}
