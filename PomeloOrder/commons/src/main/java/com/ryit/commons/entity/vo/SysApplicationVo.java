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
public class SysApplicationVo implements Serializable {

    private static final long serialVersionUID = -1723452802156943998L;

    /**
     * 主键
     */
    private Long id;

    /**
     * app名称
     */
    private String name;

    /**
     * app编码
     */
    private String code;

    /**
     * 创建人
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

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

    /**
     * 创建时间
     */
    private Date createTime;


    public static SysApplicationVo buildVo(SysApplication po) {
        SysApplicationVo vo = new SysApplicationVo();
        BeanUtils.copyProperties(po, vo);
        vo.setUpdateFlag(po.getUpdateFlag() ? 1 : 0);
        return vo;
    }

    public static List<SysApplicationVo> buildVoList(List<SysApplication> poList) {
        List<SysApplicationVo> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(poList)){
            return voList;
        }
        poList.forEach(po->{
            voList.add(buildVo(po));
        });
        return voList;
    }

}