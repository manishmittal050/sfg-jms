package com.mmittal.sfgjms.consumer;

import com.mmittal.sfgjms.config.JmsConfig;
import com.mmittal.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloWorldListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message){

        System.out.println("I got a  Message!!!!");
        System.out.println(helloWorldMessage);

    }

    @JmsListener(destination = JmsConfig.MY_SND_RCV_QUEUE)
    public void listenAcknowleged(@Payload HelloWorldMessage helloWorldMessage,
                                  @Headers MessageHeaders headers,
                                  Message message) throws JMSException {

        HelloWorldMessage acknowlegementMsg = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Received!")
                .build();


        jmsTemplate.convertAndSend(message.getJMSReplyTo(), acknowlegementMsg);
//        System.out.println("I got a  Message!!!!");
//        System.out.println(helloWorldMessage);

    }

}
