package com.microservice.orchestration.demo.com.microservice.orchestration.adapter;

import com.microservice.orchestration.demo.entity.ServiceRequest;
import com.microservice.orchestration.demo.entity.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 *
 * @author <a href="mailto:me@mocciavincenzo.it">Vincenzo Moccia</a>
 *
 */

public class JmsRpcClient {

    @Autowired
    JmsMessagingTemplate jmsTemplate;

    public ServiceResponse invokeService(ServiceRequest serviceRequest) {
        jmsTemplate.getJmsTemplate().setReceiveTimeout(20000);
        ServiceResponse response = jmsTemplate.convertSendAndReceive(serviceRequest.getServiceName()+".queue",
                serviceRequest, ServiceResponse.class);
        return response;
    }
}
