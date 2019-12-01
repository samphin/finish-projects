package com.ryit.commons.entity.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资质设置信息列表对象
 * @author : samphin
 * @Date : 2019-9-9 11:57:12
 */
@Getter
@Setter
@NoArgsConstructor
public class SysDictListVo implements Serializable {

    private static final long serialVersionUID = 5789969369626727480L;

    private Long id;

    private String dictLabel;

    private String dictType;

    private String remark;

    private Integer creditScore;
}
