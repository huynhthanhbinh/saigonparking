package com.bht.saigonparking.service.mail.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.mail.MailRequest;
import com.bht.saigonparking.api.grpc.mail.MailRequestType;

import lombok.Setter;

/**
 *
 * @author bht
 */
@Service
@Transactional
public class MailServiceImpl implements MailService {

    @Setter(onMethod = @__(@Autowired))
    private JavaMailSender mailSender;

    @Value("${saigonparking.domain}")
    private String saigonParkingDomain;

    @Override
    public void sendNewMail(MailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        boolean isActivateEmail = request.getType().equals(MailRequestType.ACTIVATE_ACCOUNT);
        String subject = isActivateEmail
                ? "[Saigon Parking] Activate new account"
                : "[Saigon Parking] Reset password";

        String content = composeMail(isActivateEmail, request.getUsername(), request.getAccessToken());

        message.setTo(request.getEmail());
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    private String composeMail(boolean isActivateEmail, String username, String token) {
        return "Dear " + username + ",\n\n"
                + "Please click in the link below to "
                + ((isActivateEmail) ? "activate your account" : "reset your account password")
                + ":\n\n"
                + saigonParkingDomain + "/"
                + ((isActivateEmail) ? "activate-account" : "reset-password")
                + "?token=" + token
                + "\n"
                + ""
                + "\nPlease notice that the link we provide above will be expired in an hour !"
                + "\nPlease do not reply this email as this is an automatic generated email  !"
                + "\n\nYours sincerely, Saigon Parking VN.";
    }
}