package com.bht.saigonparking.service.mail.service;

import com.bht.saigonparking.api.grpc.mail.MailRequest;

/**
 *
 * @author bht
 */
public interface MailService {

    /**
     * send new email to some user
     * @param request mail request, include:
     * + mailType: activate-account or reset-password ?
     * + username: username of user to whom the mail is sent
     * + email: email of user to whom the mail is sent
     * + token: using for access, will expired in just 5 min
     */
    void sendNewMail(MailRequest request);
}