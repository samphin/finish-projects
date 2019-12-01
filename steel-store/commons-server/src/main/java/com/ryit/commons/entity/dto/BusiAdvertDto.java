package com.ryit.commons.entity.dto;

import com.ryit.commons.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 广告管理
 *
 * @author samphin
 * @since 2019-10-31 14:24:00
 */
@Data
@ApiModel(value = "广告信息对象")
public class BusiAdvertDto extends BaseDto<Long> implements Serializable {

    private static final long serialVersionUID = 8448118297431613015L;

    /**
     * 广告标题
     */
    @ApiModelProperty(value = "广告标题")
    @NotBlank(message = "广告标题不能为空", groups = {Save.class, Update.class})
    private String title;

    /**
     * 广告分类 0 商品素材 1 滚动横幅 2分层广告 3 三方块广告 4 四方块广告 5邀请海报
     */
    @ApiModelProperty(value = "广告分类：0 商品素材 1 滚动横幅 2分层广告 3 三方块广告 4 四方块广告 5邀请海报", allowableValues = "0,1,2,3,4,5")
    @NotNull(message = "广告类型不能为空", groups = {Save.class, Update.class})
    private Integer type;

    /**
     * 广告链接
     */
    @ApiModelProperty(value = "广告链接")
    @NotBlank(message = "广告连接不能为空", groups = {Save.class, Update.class})
    private String url;

    /**
     * 是否发布 0否 1是
     */
    @ApiModelProperty(value = "是否发布：0否 1是", allowableValues = "0,1")
    private Integer releaseStatus;

    /**
     * 是否有效 0否 1是
     */
    @ApiModelProperty(value = "是否有效：0否 1是", allowableValues = "0,1")
    private Integer validStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否是首页广告 0否 1是
     */
    @ApiModelProperty(value = "是否是首页广告：0否 1是", allowableValues = "0,1")
    private Integer homeStatus;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间：格式yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间：格式yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 广告图片
     */
    @ApiModelProperty(value = "广告图片数组")
    private List<String> images;
}