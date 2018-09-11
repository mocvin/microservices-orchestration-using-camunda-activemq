/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microservice.orchestration.demo;

import com.microservice.orchestration.demo.com.microservice.orchestration.adapter.BrokerConfig;
import com.microservice.orchestration.demo.com.microservice.orchestration.adapter.JmsPubClient;
import com.microservice.orchestration.demo.com.microservice.orchestration.adapter.JmsRpcClient;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import java.sql.SQLException;

/**
 * @author <a href="mailto:colinlucs@gmail.com">Colin Lu</a>
 * @author <a href="mailto:me@mocciavincenzo.it">Vincenzo Moccia</a>
 *
 */
@SpringBootApplication
@EnableProcessApplication
@EnableJms
public class ShoppingCartServiceApplication {

	public static void main(String... args) {
		SpringApplication.run(ShoppingCartServiceApplication.class, args);
	}

	@Autowired
	BrokerConfig brokerConfig;

	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerConfig.getUrl());
		connectionFactory.setPassword(brokerConfig.getPassword());
		connectionFactory.setUserName(brokerConfig.getUser());
		connectionFactory.setTrustAllPackages(true);
		return connectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		return template;
	}

	public JmsMessagingTemplate jmsMessagingTemplate() {
		JmsMessagingTemplate template = new JmsMessagingTemplate();
		template.setConnectionFactory(connectionFactory());
		return template;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		factory.setConcurrency("1-1");
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	public JmsPubClient jmsPubClient() {
		return new JmsPubClient();
	}


	@Bean
	public JmsRpcClient jmsRpcClient() {
		return new JmsRpcClient();
	}

}
