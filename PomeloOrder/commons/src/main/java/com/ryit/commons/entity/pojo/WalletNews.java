package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: zhangweixun
 * @Date: 2019/9/5 0005下午 6:26
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletNews implements Serializable {
    private static final long serialVersionUID = -7887980514269105305L;
    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Long id;

    /**
     * 资讯标题
     * isNullAble:1
     */
    private String newsTitle;

    /**
     * 标题图
     * isNullAble:1
     */
    private String titleImg;

    /**
     * 是否推荐 0推荐 1不推荐
     * isNullAble:1
     */
    private Integer recommendFlag;

    /**
     * 推荐级别 从大到小 级别越高越推荐
     * isNullAble:1,defaultVal:0
     */
    private Integer recommendLevel;

    /**
     * 阅读数
     * isNullAble:1
     */
    private Integer readCount;

    /**
     * 发布时间
     * isNullAble:1
     */
    private Date releaseTime;

    /**
     * 来源
     * isNullAble:1
     */
    private String origin;

    /**
     * 删除标志位 0:未删除 1：已删除
     * isNullAble:1
     */
    private Integer delFlag;

    /**
     * 资讯内容
     * isNullAble:1
     */
    private String txt;
}
