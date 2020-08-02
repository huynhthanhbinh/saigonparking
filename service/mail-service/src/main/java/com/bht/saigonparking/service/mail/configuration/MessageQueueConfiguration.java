package com.bht.saigonparking.service.mail.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.MAIL_QUEUE_NAME;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.bht.saigonparking.api.grpc.mail.MailRequest;
import com.bht.saigonparking.service.mail.service.MailService;

import lombok.AllArgsConstructor;

/**
 *
 * @author bht
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageQueueConfiguration {

    private final MailService mailService;

    @Async
    @RabbitListener(queues = {MAIL_QUEUE_NAME})
    public void consumeMessageFromMailTopic(MailRequest request) {
        mailService.sendNewMail(request);
    }
}