package com.cicloi.activemq.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import java.util.UUID;

@Component
public class Sender {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Sender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String message) {
        LOGGER.info("sending message='{}'", message);
        jmsTemplate.send(session -> {
            Message msg = session.createMessage();
            msg.setJMSMessageID(UUID.randomUUID().toString());
            msg.setStringProperty("JMSXGroupID", "Group-0");
            return msg;
        });
    }
}
