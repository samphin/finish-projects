package com.ryit.orderserver.dao;

import com.ryit.commons.entity.pojo.SysOrderSetting;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysOrderSettingMapper {

    int insertBatch(List<SysOrderSetting> records);

    int clearAllData();

    List<SysOrderSetting> selectAllCondition();
}