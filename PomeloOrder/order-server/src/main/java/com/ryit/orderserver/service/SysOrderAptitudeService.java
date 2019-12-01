package com.ryit.orderserver.service;

import com.ryit.commons.entity.pojo.SysOrderAptitude;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/18 0018上午 11:36
 */
public interface SysOrderAptitudeService {

    /**
     * 批量添加资质信息
     *
     * @param list
     * @return
     */
    Integer batchInsertAptitude(List<SysOrderAptitude> list);
}
