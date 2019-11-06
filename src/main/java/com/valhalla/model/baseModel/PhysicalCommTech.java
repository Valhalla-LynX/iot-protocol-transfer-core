package com.valhalla.model.baseModel;

/**
 * @author Valhalla
 * @description Physical transmission technology
 * @date 2019/10/23
 **/
public enum PhysicalCommTech {
    RS485("RS485");
    private String name;
    PhysicalCommTech(String name) {
        this.name = name;
    }
}
