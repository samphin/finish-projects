package com.ryit.orderserver.service.impl;

import com.ryit.commons.entity.pojo.SysOrderAptitude;
import com.ryit.orderserver.dao.SysOrderAptitudeMapper;
import com.ryit.orderserver.service.SysOrderAptitudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/18 0018上午 11:37
 */
@Service
public class SysOrderAptitudeServiceImpl implements SysOrderAptitudeService {

    @Autowired
    private SysOrderAptitudeMapper sysOrderAptitudeMapper;

    @Override
    public Integer batchInsertAptitude(List<SysOrderAptitude> list) {
        for (SysOrderAptitude aptitude : list) {
            if (aptitude.getScore().equals(0)) {
                aptitude.setPriority(0);
            } else {
                aptitude.setPriority(1);
            }
        }
        return sysOrderAptitudeMapper.batchInsertAptitude(list);
    }
}
