package com.ryit.orderserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.pojo.SysApplication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysApplicationMapper extends BaseMapper<Long, SysApplication> {

    /**
     * 根据APP编码获取app信息
     *
     * @author samphin
     * @date 2019-10-11 14:49:23
     */
    SysApplication selectByCode(@Param("code") String code);

    /**
     * 校验版本号是否存在
     *
     * @param version
     * @author samphin
     * @date 2019-10-11 17:11:06
     */
    Integer selectByVersion(@Param("version") String version);

    /**
     * 通过app编码查询最新版本号
     * @author samphin
     * @date 2019-10-11 19:30:09
     */
    String selectMaxVersion(@Param("code") String code);
}