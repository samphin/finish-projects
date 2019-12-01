package com.ryit.orderserver.dao;

import com.ryit.commons.entity.pojo.SysOrderAptitude;
import com.ryit.commons.entity.pojo.SysOrderAptitudeTemp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysOrderAptitudeTempMapper {

    /**
     * 查询订单资质
     *
     * @param orderId
     * @return
     * @author samphin
     * @date 2019-10-9 19:00:56
     */
    List<SysOrderAptitudeTemp> selectAllByOrderId(@Param("orderId") Long orderId);

    /**
     * 清空订单资质信息
     * @param orderId
     * @author samphin
     * @date 2019-10-9 19:00:43
     */
    Integer clearOrderAptitude(@Param("orderId") Long orderId);

    /**
     * 批量添加资质信息
     *
     * @param list
     * @author samphin
     * @date 2019-10-9 19:00:52
     */
    Integer batchInsertAptitude(List<SysOrderAptitudeTemp> list);
}