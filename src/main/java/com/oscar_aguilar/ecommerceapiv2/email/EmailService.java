package com.oscar_aguilar.ecommerceapiv2.email;

public interface EmailService {
    void sendHtmlEmail(EmailDetails emailDetails);

    void sendHtmlEmailWithAttachments(EmailDetails emailDetails);
}
