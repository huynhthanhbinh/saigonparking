package com.bht.saigonparking.service.mail.configuration;

import static com.bht.saigonparking.common.constant.SaigonParkingMessageQueue.MAIL_QUEUE_NAME;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author bht
 */
@Log4j2
@Component
public final class MessageQueueConfiguration {

    @RabbitListener(queues = {MAIL_QUEUE_NAME})
    public void receiveMessageFromMailTopic(String message) {
        log.info("Consume message from mail topic: " + message);
    }
}