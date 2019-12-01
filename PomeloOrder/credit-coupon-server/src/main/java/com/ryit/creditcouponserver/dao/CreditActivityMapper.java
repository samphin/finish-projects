package com.ryit.creditcouponserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.pojo.CreditActivity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditActivityMapper extends BaseMapper<Long, CreditActivity> {

    /**
     * 查询所有需要展示的活动信息
     *
     * @return
     * @author samphin
     * @date 2019-8-30 09:59:29
     */
    List<CreditActivity> selectVisibleActivity();
}