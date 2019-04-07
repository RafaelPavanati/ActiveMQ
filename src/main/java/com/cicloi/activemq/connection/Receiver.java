package com.cicloi.activemq.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;


@Component
public class Receiver implements MessageListener {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

    /**
     * Podemos  aqui definir um conteinerFactory, pois nossa aplicação pode ter mais de uma configuração
     * e se conectar a mais de um broker
     */

    @JmsListener(destination = "queue.teste")
    public void onMessage(Message message) {
        LOGGER.info("received message: '{}'", message);
    }

    @JmsListener(destination = "topic.teste", containerFactory = "jmsFactoryTopic")
    public void onMessageTopic(Message message) {
        LOGGER.info("received message: '{}'", message);
    }
}