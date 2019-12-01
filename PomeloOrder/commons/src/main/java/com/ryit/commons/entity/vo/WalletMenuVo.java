package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletMenu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/14 0014下午 2:44
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletMenuVo extends WalletMenu {

    /**
     * 子菜单
     */
    private List<WalletMenuVo> child;
}
