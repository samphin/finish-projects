package com.ryit.users.mapper;

import com.ryit.commons.entity.pojo.BusiAdvertImg;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BusiAdvertImgMapper extends Mapper<BusiAdvertImg> {

    /**
     * 批量新增
     *
     * @param images
     * @return
     */
    Integer insertBatch(List<BusiAdvertImg> images);

    /**
     * 删除广告图片
     *
     * @param advertId
     * @return
     */
    Integer deleteByAdvertId(@Param("advertId") Long advertId);

    /**
     * 查询广告图片
     *
     * @param advertId
     * @return
     */
    List<String> selectImagesByAdvertId(@Param("advertId") Long advertId);
}