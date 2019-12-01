package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.SysApplication;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统应用Vo类
 *
 * @author samphin
 * @date 2019-10-11 13:53:07
 */
@Getter
@Setter
@ToString
public class SysApplicationVersionVo implements Serializable {

    private static final long serialVersionUID = -6101711518563204484L;

    /**
     * 版本号
     */
    private String version;

    /**
     * APP下载地址
     */
    private String downloadPath;

    /**
     * 是否强制更新->0：否；1：是
     */
    private Integer updateFlag;

    public static SysApplicationVersionVo buildVo(SysApplication po) {
        SysApplicationVersionVo vo = new SysApplicationVersionVo();
        BeanUtils.copyProperties(po, vo);
        vo.setUpdateFlag(po.getUpdateFlag() ? 1 : 0);
        return vo;
    }
}