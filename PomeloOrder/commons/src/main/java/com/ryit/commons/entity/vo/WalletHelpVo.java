package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletHelp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangweixun
 * @Date: 2019/10/15 0015下午 7:49
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletHelpVo implements Serializable {

    /**
     * 七一钱包帮助列表
     */
    private List<WalletHelp> list;

    /**
     * 客服电话
     */
    private String servicePhone;
}
