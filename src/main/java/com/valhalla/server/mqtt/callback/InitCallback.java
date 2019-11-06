package com.valhalla.server.mqtt.callback;

import com.valhalla.server.mqtt.MQTTPublisher;
import org.fusesource.mqtt.client.Callback;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/4
 **/
public class InitCallback implements Callback {
    private MQTTPublisher mqttPublisher;

    public InitCallback(MQTTPublisher mqttPublisher) {
        this.mqttPublisher = mqttPublisher;
    }

    @Override
    public void onSuccess(Object o) {
//        mqttPublisher.setRunning(true);
        mqttPublisher.thread.start();
    }

    @Override
    public void onFailure(Throwable throwable) {
        System.out.println("system:mqttPublisher:init fail");
        throwable.printStackTrace();
    }
}
