package com.valhalla.server.ask;

import com.valhalla.server.mqtt.MQTTConfig;
import com.valhalla.server.mqtt.MQTTPublisher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author Valhalla
 * @description this server works for the converters to ask for data from rs485 devices,
 * transfers messages from socket tcp server（netty） to mqtt(emqx)
 * @date 2019/10/31
 **/
@Service
public class AskServer {
    @Value("${askserver.port}")
    private int port;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerBootstrap bootstrap = new ServerBootstrap();
    @Autowired
    private ServiceHandlerInitializer serviceHandlerInitializer;
    @Autowired
    private MQTTConfig mqttConfig;

    public MQTTPublisher mqttPublisher;

    public void start() {
        try {
            mqttPublisher  = MQTTPublisher.getInstance(mqttConfig);
            while (mqttPublisher.getRunning() == false) {
                // waiting for publiser init finish
                Thread.sleep(1000);
            }
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(serviceHandlerInitializer);
            // 绑定端口，开始接收进来的连接
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException ie) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            ie.printStackTrace();
        }
    }
}
