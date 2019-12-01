package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统应用Dto类
 *
 * @author samphin
 * @date 2019-10-11 13:53:07
 */
@Getter
@Setter
@ToString
public class SysApplicationDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = -4903043097525310204L;

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
}