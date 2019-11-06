package com.valhalla.model.baseModel;

/**
 * @author Valhalla
 * @description Data Frame
 * @date 2019/10/23
 **/
public class DataFrame {
    private byte addressLength;
    private byte functionLength;
    private byte checkLength;
    private byte[] data;

    public DataFrame(byte address, byte function, byte check ,byte[] data) {
        this.addressLength = address;
        this.functionLength = function;
        this.checkLength = check;
        this.data = data;
    }
}
