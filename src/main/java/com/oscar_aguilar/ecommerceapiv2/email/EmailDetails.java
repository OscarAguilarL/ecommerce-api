package com.oscar_aguilar.ecommerceapiv2.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String to;
    private String htmlTemplate;
    private String subject;
    private String attachment;

    public EmailDetails(String to, String htmlTemplate, String subject) {
        this.to = to;
        this.htmlTemplate = htmlTemplate;
        this.subject = subject;
    }
}
