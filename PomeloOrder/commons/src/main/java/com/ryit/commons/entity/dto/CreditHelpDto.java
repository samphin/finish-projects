package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 帮助管理入参封装类
 * @author samphin
 * @date 2019-9-4 16:15:14
 */
@Setter
@Getter
@NoArgsConstructor
public class CreditHelpDto extends BaseQueryDto implements Serializable {

    private static final long serialVersionUID = 5022813273856837041L;

    private Long id;

    private String ask;

    private Integer sort;

    private String answer;
}