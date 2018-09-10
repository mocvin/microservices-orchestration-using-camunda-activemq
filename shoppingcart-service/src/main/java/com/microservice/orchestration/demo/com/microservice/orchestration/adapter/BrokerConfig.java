package com.microservice.orchestration.demo.com.microservice.orchestration.adapter;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:me@mocciavincenzo.it">Vincenzo Moccia</a>
 *
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "broker")
public class BrokerConfig {

    private String url;
    private String user;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
