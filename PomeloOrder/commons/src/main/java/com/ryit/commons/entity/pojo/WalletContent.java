package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009下午 3:48
 * @Description: 资讯
 */
@Setter
@Getter
@NoArgsConstructor
public class WalletContent implements Serializable {

    private static final long serialVersionUID = -160509334334634339L;

    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Long id;

    /**
     * 发布时间
     * isNullAble:1
     */
    private Date createTime;

    /**
     * 是否是推荐资讯 0否 1是
     * isNullAble:1
     */
    private Integer recommendFlag;

    /**
     * 推荐级别 越大越推荐
     * isNullAble:1
     */
    private Integer recommendLevel;

    /**
     * 资讯封面
     * isNullAble:1
     */
    private String newsCover;

    /**
     * 资讯来源
     * isNullAble:1
     */
    private String origin;

    /**
     * 删除状态 0正常 2删除
     * isNullAble:1,defaultVal:0
     */
    private String delFlag;

    /**
     * 资讯标题
     * isNullAble:1
     */
    private String newsTitle;

    /**
     * 资讯内容
     * isNullAble:1
     */
    private String txt;

    /**
     * 资讯类型id
     * isNullAble:1
     */
    private Long typeId;

    /**
     * 资讯所属类型的顶级id
     * isNullAble:1
     */
    private Long parentTypeId;

    /**
     * 浏览量
     */
    private Long readCount;
}
