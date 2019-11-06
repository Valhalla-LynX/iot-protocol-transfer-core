package com.valhalla.model.encapsulation;

import com.valhalla.model.baseModel.PhysicalCommTech;
import com.valhalla.model.baseModel.SerialCommProt;

/**
 * @author Valhalla
 * @description RS485
 * @date 2019/10/23
 **/
public class Modbus extends SerialCommProt {
    private static final String name = "Modbus";

    private int codeLength;
    private int dataBits;
    private int parityBits;
    private int stopBits;
    private String check;
    private int baud;

    public Modbus() {
        super(PhysicalCommTech.RS485);
        this.codeLength = 8;
        this.dataBits = 8;
        this.parityBits = 0;
        this.stopBits = 1;
        this.check = "CRC";
        this.baud = 4800;
    }
}
