package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.BusiAdvert;
import com.ryit.commons.entity.vo.BusiHomePageAdvertVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiAdvertMapper extends Mapper<BusiAdvert> {

    /**
     * APP首页广告信息
     *
     * @return
     */
    List<BusiHomePageAdvertVo> selectAdvertInfo();

    /**
     * 查询广告列表
     * @param po
     * @return
     */
    List<BusiAdvert> selectList(BusiAdvert po);
}