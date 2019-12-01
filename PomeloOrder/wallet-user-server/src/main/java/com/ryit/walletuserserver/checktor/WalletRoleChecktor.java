package com.ryit.walletuserserver.checktor;

import com.ryit.commons.entity.dto.WalletRoleDto;
import com.ryit.commons.entity.pojo.WalletRole;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang.StringUtils;

/**
 * @author: zhangweixun
 * @Date: 2019/10/14 0014上午 9:31
 */
public class WalletRoleChecktor {

    public static void checkInsertWalletRole(WalletRoleDto walletRole) {
        if (StringUtils.isBlank(walletRole.getRoleName())) {
            throw new CustomException("角色名不能为空");
        }
        if (StringUtils.isBlank(walletRole.getRoleKey())) {
            throw new CustomException("角色字符串不能为空");
        }
        if (null == walletRole.getRoleSort()) {
            throw new CustomException("显示顺序不能为空");
        }
    }

    public static void checkUpdateWalletRole(WalletRoleDto walletRole) {
        if (null == walletRole.getRoleId()) {
            throw new CustomException("角色id不能为空");
        }
        checkInsertWalletRole(walletRole);
    }
}
