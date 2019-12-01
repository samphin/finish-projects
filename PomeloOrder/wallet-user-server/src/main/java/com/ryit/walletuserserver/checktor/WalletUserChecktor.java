package com.ryit.walletuserserver.checktor;

import com.ryit.commons.entity.pojo.WalletUser;
import com.ryit.commons.web.exception.user.CustomException;
import org.apache.commons.lang.StringUtils;

/**
 * @author: zhangweixun
 * @Date: 2019/9/23 0023下午 9:25
 * @lastUpdater samphin
 * @lastUpdateDate 2019-10-8 14:31:12
 */
public class WalletUserChecktor {


    public static void checkWalletUser(WalletUser walletUser) {
        if (StringUtils.isBlank(walletUser.getRealname())) {
            throw new CustomException("用户真实姓名不能为空");
        }
        /*if (StringUtils.isBlank(walletUser.getCreditorIdcard())) {
            throw new CustomException("用户身份证号不能为空");
        }*/
        if (StringUtils.isBlank(walletUser.getPhone())) {
            throw new CustomException("用户手机号不能为空");
        }
    }
}
