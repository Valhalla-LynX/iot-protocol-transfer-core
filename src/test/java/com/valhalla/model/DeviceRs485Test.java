package com.valhalla.model;

import com.valhalla.model.baseModel.DeviceRs485;
import com.valhalla.model.mapper.DeviceRs485Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/4
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceRs485Test {
    @Autowired
    private DeviceRs485Mapper deviceRs485Mapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        Map<String, Object> map = deviceRs485Mapper.getDeviceBasicData("9ca525211ac2");
        Assert.assertEquals(3, map.size());
        System.out.println(map);
    }
}
