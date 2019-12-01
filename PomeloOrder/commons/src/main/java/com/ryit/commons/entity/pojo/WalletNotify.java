package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/9/9 0009下午 3:41
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletNotify implements Serializable {

    private static final long serialVersionUID = 1568014887017L;


    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Integer id;

    /**
     * banner图片
     * isNullAble:1
     */
    private String banner;

    /**
     * H5链接
     * isNullAble:1
     */
    private String urlLink;

    /**
     * 是否显示(0:否1:是)
     * isNullAble:1,defaultVal:1
     */
    private Integer showFlag;
}
