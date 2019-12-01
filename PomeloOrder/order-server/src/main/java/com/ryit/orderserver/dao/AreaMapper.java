package com.ryit.orderserver.dao;

import com.ryit.commons.entity.pojo.SysArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : 刘修
 * @Date : 2019/8/29 9:58
 */
@Repository
public interface AreaMapper {

    /**
     * 查询地区列表
     *
     * @return
     */
    List<SysArea> getAreaList();

    /**
     * 根据code查询name值
     *
     * @author samphin
     * @date 2019-10-15 16:35:07
     */
    String selectNameByCode(@Param("code") Integer code);
}
