package com.microservice.orchestration.demo.com.microservice.orchestration.adapter;

import com.microservice.orchestration.demo.entity.ServiceRequest;
import com.microservice.orchestration.demo.entity.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author <a href="mailto:me@mocciavincenzo.it">Vincenzo Moccia</a>
 */
public class JmsPubClient {

    @Autowired
    JmsTemplate jmsTemplate;

    public void publishEvent(ServiceRequest request) {
        StringBuilder destinationBuilder = new StringBuilder(request.getServiceName()).append('.').append("queue");
        jmsTemplate.convertAndSend(destinationBuilder.toString(), request);
    }

    public void publishError(ServiceResponse response, String routingKey) {
        StringBuilder destinationBuilder = new StringBuilder("error.queue");
        jmsTemplate.convertAndSend(destinationBuilder.toString(), response, message -> {
            message.setStringProperty("routingKey", routingKey);
            return message;
        });
    }
}
