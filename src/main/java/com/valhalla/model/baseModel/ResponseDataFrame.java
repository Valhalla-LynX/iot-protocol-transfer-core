package com.valhalla.model.baseModel;

import java.util.Arrays;

/**
 * @author Valhalla
 * @description DataFrame
 * @date 2019/10/23
 **/
public class ResponseDataFrame {
    private byte[] bytes;
    private byte address;
    private byte function;
    //数据有效字节数
    private byte dataBytesLen;
    //数据长度
    private byte dataLen;
    private byte[] data;

    private ResponseDataFrame(byte[] bytes) {
        this.bytes = bytes;
        address = bytes[0];
        function = bytes[1];
        dataBytesLen = bytes[2];
        dataLen = 2;
        data = Arrays.copyOfRange(bytes, 3, dataBytesLen);
    }

//    public boolean checkData() {
//
//    }
}
