package com.bht.saigonparking.service.mail.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author bht
 */
@Component
public final class MailConfiguration {

    @Value("${saigonparking.domain}")
    private String saigonParkingDomain;

    @Bean("activateAccountEmailTemplate")
    public String activateAccountEmailTemplate() {
        return "<p> " +
                "Welcome to Saigon Parking, <b><i>%s</i></b>,<br/>" +
                "<br/>" +
                "Please click the link below to activate your account:<br/><br/>" +
                "<a href=\"" + saigonParkingDomain + "/activate-account?token=%s" + "\">" +
                "Activate account link" +
                "</a><br/>" +
                "<br/>" +
                "Please notice that the link we provide above will be expired in 5 minutes !<br/>" +
                "Please do not reply this email as this is an auto-generated email !<br/><br/>" +
                "Yours sincerely, Saigon Parking VN. " +
                "</p>";
    }

    @Bean("resetPasswordEmailTemplate")
    public String resetPasswordEmailTemplate() {
        return "<p> " +
                "Dear <b><i>%s</i></b>,<br/>" +
                "<br/>" +
                "Please click the link below to reset your account password:<br/><br/>" +
                "<a href=\"" + saigonParkingDomain + "/reset-password?token=%s" + "\">" +
                "Reset password link" +
                "</a><br/>" +
                "<br/>" +
                "Please notice that the link we provide above will be expired in 5 minutes !<br/>" +
                "Please do not reply this email as this is an auto-generated email !<br/><br/>" +
                "Yours sincerely, Saigon Parking VN. " +
                "</p>";
    }
}