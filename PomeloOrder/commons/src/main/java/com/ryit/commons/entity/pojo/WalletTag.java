package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: zhangweixun
 * @Date: 2019/10/11 0011下午 7:16
 * @Description: 钱包商品标签实体
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletTag implements Serializable {

    private static final long serialVersionUID = -7105213283397618952L;

    /**
     * 主键
     *
     * isNullAble:0
     */
    private Long id;

    /**
     *
     * isNullAble:1
     */
    private String name;

    /**
     *
     * isNullAble:1
     */
    private String code;

    /**
     *
     * isNullAble:1
     */
    private Integer sort;

    /**
     * 创建时间
     * isNullAble:1
     */
    private Date createTime;

    /**
     * 删除状态 0正常 2删除
     * isNullAble:1,defaultVal:0
     */
    private String delFlag;
}
