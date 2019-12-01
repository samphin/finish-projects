package com.ryit.orderserver.dao;

import com.ryit.commons.entity.pojo.SysDict;
import com.ryit.commons.entity.vo.SysDictListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictMapper {

    /**
     * 查询系统所有字典
     *
     * @param sysDict
     * @return
     * @author samphin
     * @date 2019-8-31 17:12:17
     */
    List<SysDict> selectAllDict(SysDict sysDict);

    /**
     * 订单资质分值设置列表
     *
     * @return
     * @author samphin
     * @date 2019-9-21 16:09:58
     */
    List<SysDictListVo> selectAllAptitudeByOrderSetting();

    /**
     * 根据字典ID修改资质信息
     *
     * @param record
     * @author samphin
     * @date 2019-9-9 12:06:23
     */
    int updateAptitude(SysDict record);

    String getPomeloFlag();

    /**
     * 查询最大额度字典
     *
     * @return
     */
    SysDict getMaxQuota();
}