package com.ryit.orderserver.service;

import com.ryit.commons.entity.dto.SysOrderDto;
import com.ryit.commons.entity.dto.SysOrderSettingDto;
import com.ryit.commons.entity.pojo.SysOrderSetting;
import com.ryit.commons.entity.vo.SysOrderSettingVo;
import com.ryit.commons.entity.vo.SysOrderVo;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Map;

public interface ISysOrderSettingService {

    Boolean saveOrderSetting(List<SysOrderSettingDto> dtoList);

    List<SysOrderSettingVo> queryAllCondition();
}
