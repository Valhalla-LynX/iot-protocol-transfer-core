package com.valhalla.server.mqtt.callback;

import org.fusesource.mqtt.client.Callback;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/1
 **/
public class CommonCallback<T> implements Callback<T> {
    private String success;
    private String exception;

    public CommonCallback(String success, String exception) {
        this.success = success;
        this.exception = exception;
    }

    @Override
    public void onSuccess(T t) {
        System.out.println(success);
    }

    @Override
    public void onFailure(Throwable throwable) {
        System.out.println(exception);
        throwable.printStackTrace();
    }
}
