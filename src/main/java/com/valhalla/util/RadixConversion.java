package com.valhalla.util;

/**
 * @author Valhalla
 * @description Conversion
 * @date 2019/10/23
 **/
public class RadixConversion {
    public static String bytes2Hex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }

    public static String bytes2MAC(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n <6; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xff).toUpperCase();
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }

    public static String bytesData2DoubleStr(byte[] bytes, int position, int length, int point) {
        double d = 0;
        for (int i = 0; i < length; i++) {
            d += (bytes[position + i] & 0xff) << ((length - 1 - i) * 8);
        }
        return String.format("%.1f", d * 0.1 * point);
    }

    public static String bytesData2DoubleStr(byte[] bytes, int position, int length) {
        return bytesData2DoubleStr(bytes, position, length, 1);
    }

    public static double bytesData2Double(byte[] bytes, int position, int length, int point) {
        return Double.valueOf(bytesData2DoubleStr(bytes, position, length, point));
    }

    public static double bytesData2Double(byte[] bytes, int position, int length) {
        return Double.valueOf(bytesData2DoubleStr(bytes, position, length));
    }
}
