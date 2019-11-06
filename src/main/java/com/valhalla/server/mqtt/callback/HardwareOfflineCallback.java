package com.valhalla.server.mqtt.callback;

import io.netty.channel.ChannelHandlerContext;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Topic;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/1
 **/
public class HardwareOfflineCallback<T> implements Callback<T> {
    public static final String exception_connetcion = "system:mqtt-publishCallbackz注销连接异常";
    private Topic topic;
    private String text;

    public HardwareOfflineCallback(Topic topic, String text) {
        this.topic = topic;
        this.text = text;
    }

    @Override
    public void onSuccess(T t) {
        System.out.println("向主题"+topic.name().toString()+"注销了"+text);
    }

    @Override
    public void onFailure(Throwable throwable) {
        System.out.println(exception_connetcion);
        throwable.printStackTrace();
    }
}
