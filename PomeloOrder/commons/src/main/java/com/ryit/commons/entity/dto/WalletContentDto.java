package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/10/11 0011下午 1:47
 */
@Setter
@Getter
@NoArgsConstructor
public class WalletContentDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 2280829209222330078L;

    /**
     * 资讯分类id
     */
    private Long typeId;
}
