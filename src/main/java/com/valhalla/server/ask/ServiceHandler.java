package com.valhalla.server.ask;

import com.valhalla.model.baseModel.DeviceRs485;
import com.valhalla.model.mapper.DeviceRs485Mapper;
import com.valhalla.server.datapackage.DataFrame;
import com.valhalla.server.mqtt.MQTTPublisher;
import com.valhalla.util.RadixConversion;
import com.valhalla.util.exception.CRCException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Valhalla
 * @description
 * @date 2019/10/31
 **/
@ChannelHandler.Sharable
@Service
public class ServiceHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Autowired
    private DeviceRs485Mapper deviceRs485Mapper;
    private Map<String, Object> deviceRs485basicData;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        for (int i=0;i<bytes.length;i++) {
            bytes[i]&=0xff;
        }
        if (bytes.length == 6) {
            System.out.println("Server received: register:" + RadixConversion.bytes2Hex(bytes));
        } else {
            System.out.println("Server received: " + RadixConversion.bytes2Hex(bytes));
            try {
                MQTTPublisher.queue.offer(new DataFrame(deviceRs485basicData, bytes, 3, 2, 1, 2));
//                MQTTPublisher.queue.offer(new DataFrame(deviceRs485basicData, bytes, 5, 2, 1));
            } catch (CRCException crce) {
                System.out.println("system:crc error-data deprecate");
            }

            System.out.println("~~~~~ MQTT Publish finish:"+channelHandlerContext.name()+" ~~~~~");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        deviceRs485basicData = deviceRs485Mapper.getDeviceBasicData("9ca525211ac2");
        MQTTPublisher.queue.offer(new DataFrame(deviceRs485basicData, true));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        MQTTPublisher.queue.offer(new DataFrame(deviceRs485basicData, false));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof ReadTimeoutException) {
            System.out.println("Long time no read.Device offline: channel close.");
        } else {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
