package com.ryit.commons.entity.pojo;

import com.ryit.commons.entity.dto.SysApplicationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统应用表
 *
 * @author samphin
 * @date 2019-10-11 13:53:07
 */
@Getter
@Setter
@ToString
public class SysApplication implements Serializable {

    private static final long serialVersionUID = 66725139473620073L;

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
    private Boolean updateFlag;

    /**
     * 创建人
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    @Transient
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * dto->po
     *
     * @param dto
     * @return
     */
    public SysApplication buildPo(SysApplicationDto dto) {
        if (null == dto) {
            return null;
        }
        BeanUtils.copyProperties(dto, this);
        this.setUpdateFlag(null != dto.getUpdateFlag() && 1 == dto.getUpdateFlag() ? true : false);
        return this;
    }
}