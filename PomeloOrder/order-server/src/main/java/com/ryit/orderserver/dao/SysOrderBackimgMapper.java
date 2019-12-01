package com.ryit.orderserver.dao;

import com.ryit.commons.entity.pojo.SysOrderBackimg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysOrderBackimgMapper {

    List<SysOrderBackimg> selectOrderBackImage(@Param("orderId") Long orderId);
}