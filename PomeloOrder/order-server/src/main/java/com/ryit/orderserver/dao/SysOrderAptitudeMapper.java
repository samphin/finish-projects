package com.ryit.orderserver.dao;

import com.ryit.commons.entity.dto.SysOrderQueryDto;
import com.ryit.commons.entity.pojo.SysOrderAptitude;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysOrderAptitudeMapper {


    /**
     * 查询订单资质
     *
     * @param orderId
     * @return
     * @author samphin
     * @date 2019-9-17 19:38:05
     */
    List<SysOrderAptitude> selectAllByOrderId(@Param("orderId") Long orderId);

    /**
     * 批量添加资质信息
     *
     * @param list
     * @author samphin
     * @date 2019-9-25 11:51:05
     * @return
     */
    Integer batchInsertAptitude(List<SysOrderAptitude> list);

    /**
     * @param sysOrderQueryDto
     * @author samphin
     * @date 2019-9-18 15:53:01
     */
    List<Long> selectOrderIdByCondition(SysOrderQueryDto sysOrderQueryDto);
}