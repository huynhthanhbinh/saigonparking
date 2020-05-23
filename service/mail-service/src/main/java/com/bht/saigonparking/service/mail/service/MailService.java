package com.bht.saigonparking.service.mail.service;

import com.bht.saigonparking.api.grpc.mail.MailRequest;

/**
 *
 * @author bht
 */
public interface MailService {

    void sendNewMail(MailRequest request);
}