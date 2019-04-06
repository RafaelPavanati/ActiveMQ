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

            /**
             * Ao definir JMSXGroupID As mensagens em um grupo de mensagens são sempre consumidas pelo mesmo consumidor, mesmo se
             * houver muitos consumidores em uma fila. Eles fixam todas as mensagens com o mesmo ID de grupo para o mesmo consumidor.
             * Este recurso pode ser usado se caso for necessário que todas as mensagens de um determinado cliente
             * seja executado pelo mesmo consumidor passando assim como parametro o contexto para o JMSXGroupID
             */
            msg.setStringProperty("JMSXGroupID", "Group-0");
            return msg;
        });
    }
}
