package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/10 0010下午 4:04
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletUserVo extends WalletUser {

    /**
     * 用户订单数量
     */
    private Integer orderCount;

    /**
     * 用户是否认证
     */
    private Boolean isAccreditation;

    /**
     * 添加后台管理员时赋予管理员角色id
     */
    private Long roleId;

    /**
     * 给管理员赋予的角色名
     */
    private String roleName;
}
