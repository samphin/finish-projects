package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/9/19 0019下午 1:37
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletRoleVo extends WalletRole {

    /**
     * 角色关联的菜单的子菜单的集合
     */
    private List<Long> menuIds;

}
