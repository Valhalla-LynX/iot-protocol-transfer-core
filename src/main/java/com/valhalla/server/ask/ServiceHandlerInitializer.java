package com.valhalla.server.ask;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Valhalla
 * @description ServerHandlerInitializer
 * @date 2019/10/31
 **/
@Component
public class ServiceHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private ServiceHandler serviceHandler;
    @Value("${askserver.cycle}")
    private int cycle;
    @Value("${askserver.overtime}")
    private int overtime;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast("idleStateHandler", new IdleStateHandler(cycle, 0, 0))
                .addLast("idleStateTrigger", new ServiceHandlerTrigger())
                /*
                .addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0 , 0, 0, 0))
                .addLast("frameEncoder", new LengthFieldPrepender(2))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder())
                */
                .addLast(new ReadTimeoutHandler(overtime))
                .addLast(serviceHandler);

    }
}
