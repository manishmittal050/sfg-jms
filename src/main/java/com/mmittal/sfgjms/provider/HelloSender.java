package com.mmittal.sfgjms.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmittal.sfgjms.config.JmsConfig;
import com.mmittal.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        System.out.println("I'm sending a message");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();


        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorldMessage);
        System.out.println("Message sent ... ");
    }
    @Scheduled(fixedRate = 2000)
    public void sendReceiveMessage() throws JMSException {
        System.out.println("I'm sending a message");
        HelloWorldMessage helloWorldMessage = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();


         Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SND_RCV_QUEUE ,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage= null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(helloWorldMessage));
                    System.out.println("helo mesage is "+ helloMessage);

                    helloMessage.setStringProperty("_type","com.mmittal.sfgjms.model.HelloWorldMessage");
                    System.out.println("Sending Hello... ");
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }
            }
        });

        System.out.println(receivedMessage.getBody(String.class));

    }

}
