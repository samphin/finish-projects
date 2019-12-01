package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.WalletContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: zhangweixun
 * @Date: 2019/10/10 0010下午 2:25
 */
@Getter
@Setter
@NoArgsConstructor
public class WalletContentVo extends WalletContent {

    /**
     * 对查询的时间与当前时间进行比对 得出的字符串 如 1天前
     */
    private String date;
}
