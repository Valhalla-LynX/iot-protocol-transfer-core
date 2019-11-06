package com.valhalla.model.baseModel;

import lombok.Data;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/4
 **/
@Data
public class DeviceRs485 {
    private int id;
    private String name;
    private int converter_id;
    private int type_id;
    private int address;
}
