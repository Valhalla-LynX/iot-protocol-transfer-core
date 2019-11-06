package com.valhalla.server.mqtt.callback;

import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Topic;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/1
 **/
public class HardwareOnlineCallback<T> implements Callback<T> {
    public static final String exception_content = "system:mqtt-publishCallbackz注册信息异常";
    private Topic topic;
    private String text;

    public HardwareOnlineCallback(Topic topic, String text) {
        this.topic = topic;
        this.text = text;
    }

    @Override
    public void onSuccess(T t) {
        String success = "向主题"+topic.name().toString()+"注册了"+text;
    }

    @Override
    public void onFailure(Throwable throwable) {
        System.out.println(exception_content);
        throwable.printStackTrace();
    }
}
