package com.valhalla.server;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Valhalla
 * @description
 * @date 2019/10/24
 **/
public class MQTTSub {
    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("192.160.25.102", 1883);
        mqtt.setClientId("protocolCoreGet");
        mqtt.setUserName("protocolCore");
        mqtt.setPassword("protocolCore");
//        mqtt.setWillRetain(true);
        final CallbackConnection connection = mqtt.callbackConnection();
        connection.listener(new ExtendedListener() {
            public void onDisconnected() {
                System.out.println("lis:discon");
            }
            public void onConnected() {
                System.out.println("lis:con");
            }
            public void onPublish(UTF8Buffer topic, Buffer payload, Runnable ack) {
                // You can now process a received message from a topic.
                // Once process execute the ack runnable.
                System.out.println("lis:puback");
                ack.run();
            }
            public void onFailure(Throwable value) {
                connection.disconnect(null); // a connection failure occured.
            }
            @Override
            public void onPublish(UTF8Buffer utf8Buffer, Buffer buffer, Callback<Callback<Void>> callback) {
                System.out.println("lis:pubcall");
                System.out.println(utf8Buffer);
                System.out.println(buffer.utf8());
                callback.onSuccess(new Callback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("success");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println("fail");
                    }
                });
            }
        });
        connection.connect(new Callback<Void>() {
            public void onFailure(Throwable value) {
                System.out.println("con:fail");
//                result.failure(value); // If we could not connect to the server.
            }

            // Once we connect..
            public void onSuccess(Void v) {
                System.out.println("con:success");

                Topic[] topics = {new Topic("test", QoS.AT_MOST_ONCE)};
                connection.subscribe(topics, new Callback<byte[]>() {
                    public void onSuccess(byte[] qoses) {
                        // The result of the subcribe request.
                        System.out.println("sub:success");
                    }
                    public void onFailure(Throwable value) {
                        System.out.println("sub:fail");
                        System.out.println(value.getMessage());
                        connection.disconnect(null); // subscribe failed.
                    }
                });
            }
        });
        Thread.sleep(10000000000L);
    }
}
