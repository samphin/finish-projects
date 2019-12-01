package com.ryit.orderserver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.ryit.commons.entity.dto.SysOrderSettingDto;
import com.ryit.commons.entity.pojo.SysOrderSetting;
import com.ryit.commons.entity.vo.SysOrderSettingVo;
import com.ryit.orderserver.dao.SysOrderSettingMapper;
import com.ryit.orderserver.service.ISysOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysOrderSettingServiceImpl implements ISysOrderSettingService {

    @Autowired
    private SysOrderSettingMapper sysOrderSettingMapper;

    @Override
    @LcnTransaction
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrderSetting(List<SysOrderSettingDto> dtoList) {
        try {
            //先清除所有配置信息
            sysOrderSettingMapper.clearAllData();

            List<SysOrderSetting> poList = SysOrderSetting.buildPoList(dtoList);
            //批量插入所有新配信息
            sysOrderSettingMapper.insertBatch(poList);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SysOrderSettingVo> queryAllCondition() {
        List<SysOrderSetting> poList = sysOrderSettingMapper.selectAllCondition();

        return SysOrderSettingVo.buildVoList(poList);

    }

}
