package com.ryit.orderserver.service;

import com.ryit.commons.entity.dto.SysDictDto;
import com.ryit.commons.entity.pojo.SysDict;
import com.ryit.commons.entity.vo.SysDictListVo;
import com.ryit.commons.entity.vo.SysDictVo;
import com.ryit.commons.entity.vo.SysOrderAptitudeVo;

import java.util.List;

public interface ISysDictService {


    /**
     * APP系统字典查询
     *
     * @param isShowAstrict 是否显示非限制字段
     * @author samphin
     * @date 2019-9-3 10:09:31
     */
    List<SysDictVo> queryAllDict(boolean isShowAstrict,boolean isShowCamelCaseName,SysDict sysDict);

    /**
     * 查询所有资质信息
     *
     * @author samphin
     * @date 2019-9-9 12:06:33
     */
    List<SysDictVo> queryAllAptitude();

    /**
     * 订单资质分值设置列表
     * @author samphin
     * @date 2019-9-21 16:09:58
     * @return
     */
    List<SysDictListVo> queryAllAptitudeByOrderSetting();

    /**
     * 根据字典ID修改资质信息
     *
     * @author samphin
     * @date 2019-9-9 12:06:23
     */
    boolean updateAptitude(SysDictDto dictDto);

    String getPomeloFlag();

    /**
     * 查询七一钱包首页最大贷款额度
     *
     * @return
     */
    SysDict getMaxQuota();
}
