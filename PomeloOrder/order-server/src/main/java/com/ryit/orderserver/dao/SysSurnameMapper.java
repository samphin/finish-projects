package com.ryit.orderserver.dao;

import com.ryit.commons.entity.pojo.SysSurname;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysSurnameMapper {

    /**
     * 根据关键字匹配表中是否存在该复姓
     *
     * @author samphin
     * @date 2019-10-15 16:17:43
     */
    SysSurname selectBySurname(@Param("surname") String surname);
}