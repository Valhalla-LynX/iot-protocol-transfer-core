package com.valhalla.server.ask;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * @author Valhalla
 * @description
 * @date 2019/10/31
 **/
public class ServiceHandlerTrigger extends ChannelInboundHandlerAdapter {
    private short sum = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        try {
            if (evt instanceof IdleStateEvent) {
                IdleState idleState = ((IdleStateEvent) evt).state();
                switch (idleState) {
                    case READER_IDLE:
                        System.out.println("time to ask data:" + new Date());
                        byte[] bytes1 = {0x01,0x03,0x00,0x00,0x00,0x02,(byte)0xC4,0x0B};
                        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes1);
                        ctx.writeAndFlush(byteBuf);
                        break;
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        } catch (IllegalStateException ise) {
            System.out.println("system:IdleState:连接已断开");
        } catch (Exception e) {
            System.out.println("system:IdleState:长连接异常");
            e.printStackTrace();
        }

    }
}
