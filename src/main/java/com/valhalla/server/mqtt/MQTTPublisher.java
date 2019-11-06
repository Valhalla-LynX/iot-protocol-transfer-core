package com.valhalla.server.mqtt;

import com.valhalla.server.datapackage.DataFrame;
import com.valhalla.server.mqtt.callback.*;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/1
 **/
@Component
public class MQTTPublisher implements Runnable {
    private volatile static MQTTPublisher singleton;
    private static boolean running = false;

    public static DataFrameQueue queue = new DataFrameQueue();

    private static Topic topic;
    private static MQTT mqtt;
    private static CallbackConnection connection;
    public static Thread thread;

    private MQTTPublisher(MQTTConfig mqttConfig) {
        try {
            mqtt = new MQTT();
            mqtt.setHost(mqttConfig.host,mqttConfig.port);
            mqtt.setClientId(mqttConfig.clientId);
            mqtt.setClientId(mqttConfig.clientId);
            mqtt.setUserName(mqttConfig.username);
            mqtt.setPassword(mqttConfig.password);
            mqtt.setWillRetain(mqttConfig.willRetain);
            topic = new Topic(mqttConfig.topicName, QoS.AT_MOST_ONCE);
            thread = new Thread(this);
            connection = mqtt.callbackConnection();
            connection.connect(new InitCallback(this));
        } catch (URISyntaxException urise) {
            System.out.println("system:mqtt初始化uri异常:");
            urise.printStackTrace();
        }
    }

    public static MQTTPublisher getInstance(MQTTConfig mqttConfig) {
        if (singleton == null) {
            synchronized (MQTTConfig.class) {
                if (singleton == null) {
                    singleton = new MQTTPublisher(mqttConfig);
                }
            }
        }
        return singleton;
    }

    public static void online(String s) {
        connection.publish(topic.name().toString(), s.getBytes(), topic.qos(), false, new HardwareOnlineCallback<>(topic, s));
    }

    public static void publish(String s) {
        connection.publish(topic.name().toString(), s.getBytes(), topic.qos(), false, new CommonCallback<>("publish:" + s, "exception"));
    }

    public static void offline(ChannelHandlerContext ctx, String s) {
        connection.publish(topic.name().toString(), s.getBytes(), topic.qos(), false, new HardwareOfflineCallback(topic, s));
    }

    @Override
    public void run() {
        if (running == false) {
            running = true;
            try {
                while (running) {
                    Thread.sleep(1); // .sleep(0) .Yeild()
                    DataFrame dataFrame = queue.poll();
                    if (dataFrame != null) {
                        publish(dataFrame.toJsonStr());
                    }
                }
            } catch (Exception e) {

            }
            if (running == false) {
                connection.disconnect(null);
            }
        }
    }

    public boolean getRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
