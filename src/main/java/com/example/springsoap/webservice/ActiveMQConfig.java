package com.example.springsoap.webservice;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig {

    @Value("${realtimeenrollments.activemq.host:localhost}")
    private String activemqHost;
    @Value("${realtimeenrollments.activemq.port:61616}")
    private String activemqPort;

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory  = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(String.format("failover://(tcp://%s:%s)", activemqHost, activemqPort));
        activeMQConnectionFactory.setUseAsyncSend(true);
        activeMQConnectionFactory.setAlwaysSyncSend(false);

        return  activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setSessionAcknowledgeMode(Session.DUPS_OK_ACKNOWLEDGE);

        return jmsTemplate;
    }
}
