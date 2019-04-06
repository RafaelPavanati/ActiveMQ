package com.cicloi.activemq.connection;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    /**
     * Definimos aqui um conteinerFactory, pois nossa aplicação pode ter mais de uma configuração
     * e se conectar a mais de um broker
     */
    @JmsListener(destination = "helloworld.q", containerFactory = "localConection")
    public void receive(String message) {
        LOGGER.info("received message: '{}'", message);
        latch.countDown();
    }
}