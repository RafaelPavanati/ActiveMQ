package com.cicloi.activemq.connection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@EnableJms
@Configuration
public class Connection {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        if ("".equals(user)) {
            return new ActiveMQConnectionFactory(brokerUrl);
        }
        return new ActiveMQConnectionFactory(user, password, brokerUrl);
    }

    @Bean
    public JmsListenerContainerFactory jmsFactoryTopic(ConnectionFactory connectionFactory,
                                                       DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);

        /**Usamos assinaturas duráveis para garantir a entrega da mensagem enviada para um tópico.
         *
         */

        factory.setClientId("teste");
        factory.setSubscriptionDurable(true);

        /**
         * Esse contêiner de listener sempre manterá o número mínimo de consumidores ( setConcurrentConsumers(int))
         * e aumentará lentamente  até o número máximo de consumidores setMaxConcurrentConsumers(int)
         * em caso de aumento de carga.
         */
        factory.setConcurrency("1-1");

        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplateTopic() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }
}