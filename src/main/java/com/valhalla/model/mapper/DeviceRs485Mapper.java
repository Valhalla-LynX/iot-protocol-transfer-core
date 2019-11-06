package com.valhalla.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.valhalla.model.baseModel.DeviceRs485;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DeviceRs485Mapper extends BaseMapper<DeviceRs485> {
    @Select("SELECT cr.id, dr.address,dr.type_id type FROM converter_rs485 cr LEFT JOIN device_rs485 dr ON cr.id = dr.converter_id WHERE cr.mac_address = #{id}")
    Map<String, Object> getDeviceBasicData(String id);
}
