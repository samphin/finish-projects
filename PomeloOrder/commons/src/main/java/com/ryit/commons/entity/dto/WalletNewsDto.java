package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/9/6 0006上午 9:13
 */
@Setter
@Getter
@NoArgsConstructor
public class WalletNewsDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 6790381142464766228L;

    /**
     * 资讯标题
     */
    private String newsTitle;
}
