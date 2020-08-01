package com.bht.saigonparking.service.mail.service;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bht.saigonparking.api.grpc.mail.MailRequest;
import com.bht.saigonparking.api.grpc.mail.MailRequestType;
import com.bht.saigonparking.common.util.LoggingUtil;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author bht
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Qualifier("activateAccountEmailTemplate")
    private final String activateAccountEmailTemplate;

    @Qualifier("resetPasswordEmailTemplate")
    private final String resetPasswordEmailTemplate;

    @Async
    @Override
    public void sendNewMail(MailRequest request) {
        boolean isActivateEmail = request.getType().equals(MailRequestType.ACTIVATE_ACCOUNT);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");

            String subject = isActivateEmail
                    ? "[Saigon Parking] Activate new account"
                    : "[Saigon Parking] Reset password";

            String htmlContent = isActivateEmail
                    ? String.format(activateAccountEmailTemplate, request.getUsername(), request.getTemporaryToken())
                    : String.format(resetPasswordEmailTemplate, request.getUsername(), request.getTemporaryToken());

            messageHelper.setTo(request.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true);
            mailSender.send(messageHelper.getMimeMessage());

            LoggingUtil.log(Level.INFO, "SERVICE", "Success", "send"
                    + (isActivateEmail ? "ActivateAccount" : "ResetPassword")
                    + "EmailToUser(" + request.getUsername() + ")");

        } catch (Exception exception) {

            LoggingUtil.log(Level.WARN, "SERVICE", "Fail", "send"
                    + (isActivateEmail ? "ActivateAccount" : "ResetPassword")
                    + "EmailToUser(" + request.getUsername() + "): "
                    + exception.getMessage());
        }
    }
}