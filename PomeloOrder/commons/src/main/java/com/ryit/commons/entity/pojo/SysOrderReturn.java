package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * sys_order_return
 *
 * @author
 */
@Getter
@Setter
@NoArgsConstructor
public class SysOrderReturn implements Serializable {
    private static final long serialVersionUID = 1568244654473815703L;
    private Long id;

    /**
     * 订单主键
     */
    private Long orderId;

    /**
     * 退单图片路径
     */
    private String retuenImgPath;


}