package com.ryit.goods.service.impl;

import com.ryit.commons.base.service.impl.BaseServiceImpl;
import com.ryit.commons.entity.dto.BusiGoodsActivitiesDto;
import com.ryit.commons.entity.vo.BusiGoodsActivitiesVo;
import com.ryit.goods.mapper.BusiGoodsActivitiesMapper;
import com.ryit.goods.service.IBusiGoodsActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusiGoodsActivitiesServiceImpl extends BaseServiceImpl<Long, BusiGoodsActivitiesDto, BusiGoodsActivitiesVo> implements IBusiGoodsActivitiesService {

    @Autowired
    private BusiGoodsActivitiesMapper busiGoodsActivitiesMapper;
}