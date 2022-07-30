package com.mmittal.sfgjms.config;

import org.apache.activemq.artemis.api.core.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Creaed By Manish Mittal on 2022-07-30
 */
@Configuration
public class JmsConfig {

    public static final String MY_QUEUE = "my-hello-world";
    public static final String MY_SND_RCV_QUEUE = "my-hello-second-world";

    /**
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
