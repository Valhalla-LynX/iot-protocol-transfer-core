package com.valhalla.server.datapackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valhalla.util.CRC;
import com.valhalla.util.RadixConversion;
import com.valhalla.util.exception.CRCException;
import lombok.Data;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/1
 **/
@Data
public class DataFrame {
    protected long time;
    protected double[] data;
    protected Map<String, Object> basicData;

    public DataFrame(Map<String, Object> basicData, boolean isOnline) {
        this.time = Calendar.getInstance().getTimeInMillis();
        this.basicData = basicData;
        this.data = new double[1];
        if (isOnline) {
            this.data[0] = 1;
        } else {
            this.data[0] = 0;
        }
    }

    public DataFrame(Map<String, Object> basicData, byte[] bytes, int position, int length, int point ) throws CRCException {
        if ("0".equals(CRC.get_Modbus(bytes))) {
            this.time = Calendar.getInstance().getTimeInMillis();
            this.data = new double[1];
            this.data[0] = RadixConversion.bytesData2Double(bytes, position, length, point);
            this.basicData = basicData;
        } else {
            throw new CRCException();
        }
    }

    public DataFrame(Map<String, Object> basicData, byte[] bytes, int position, int length, int point, int plural) throws CRCException {
        if ("0".equals(CRC.get_Modbus(bytes))) {
            this.time = Calendar.getInstance().getTimeInMillis();
            this.data = new double[plural];
            for (int i = 0; i < plural; i++) {
                this.data[i] = RadixConversion.bytesData2Double(bytes, position + (i * length), length, point);
            }
            this.basicData = basicData;
        } else {
            throw new CRCException();
        }
    }

    @Override
    public String toString(){
        return new String("basicData:" + basicData + "data:"+data);
    }

    public String toJsonStr(){
        try {
            basicData.put("data", data);
            return new ObjectMapper().writeValueAsString(basicData);
        } catch (JsonProcessingException jpe) {
            System.out.println("system:json:转换json字符串错误");
            jpe.printStackTrace();
            return null;
        }
    }

}
