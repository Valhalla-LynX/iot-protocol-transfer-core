package com.valhalla.server.mqtt;

import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/6
 **/
@Component
public class MQTTConfig {
    @Value("${publisher.host}")
    public String host;
    @Value("${publisher.port}")
    public int port;
    @Value("${publisher.clientId}")
    public String clientId;
    @Value("${publisher.username}")
    public String username;
    @Value("${publisher.password}")
    public String password;
    @Value("${publisher.version}")
    public String version;
    @Value("${publisher.willRetain}")
    public boolean willRetain;
    @Value("${publisher.topicName}")
    public String topicName;
}
