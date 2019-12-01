package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006下午 1:35
 * @Description: 七一钱包帮助
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletHelp implements Serializable {

    private static final long serialVersionUID = -5039870333147610007L;

    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Integer id;

    /**
     * 问题
     * isNullAble:1
     */
    private String ask;

    /**
     * 排序
     * isNullAble:1
     */
    private Integer sort;

    /**
     * 解答
     * isNullAble:1
     */
    private String answer;
}
