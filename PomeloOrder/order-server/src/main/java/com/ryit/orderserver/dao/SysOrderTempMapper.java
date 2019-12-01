package com.ryit.orderserver.dao;

import com.ryit.commons.base.mapper.BaseMapper;
import com.ryit.commons.entity.dto.SysOrderTempQueryDto;
import com.ryit.commons.entity.pojo.SysOrderTemp;
import com.ryit.commons.entity.vo.SysOrderTempDetailVo;
import com.ryit.commons.entity.vo.SysOrderTempListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysOrderTempMapper extends BaseMapper<Long, SysOrderTemp> {

    /**
     * 查询条件订单草稿箱详情
     * @param params
     * @return
     */
    SysOrderTempDetailVo selectOrderTempDetail(Map<String,Object> params);

    /**
     * 查询草稿箱列表
     *
     * @param queryDto
     * @author samphin
     * @date 2019-10-10 14:32:43
     */
    List<SysOrderTempListVo> selectAllDraftsList(SysOrderTempQueryDto queryDto);
}