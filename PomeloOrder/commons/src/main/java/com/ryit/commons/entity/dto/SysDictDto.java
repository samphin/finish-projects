package com.ryit.commons.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author : 刘修
 * @Date : 2019/8/29 15:29
 * 系统字典
 */
@Getter
@Setter
@NoArgsConstructor
public class SysDictDto implements Serializable {


    private static final long serialVersionUID = 7581153486644586549L;

    private Long id;

    /**
     * 字典排序
     */
    private Integer dictSort;
    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典键值
     */
    private String dictValue;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 类型备注
     */
    private String remark;

    /**
     * 字典信息类型1：资质信息；2：基本信息；3：工作信息；4：贷款信息；5：其他信息
     */
    private Integer infoType;

    /**
     * 资质字段分值
     */
    private Integer creditScore;

    /**
     * 0信用无关1信用有关
     */
    private Integer creditFlag;
}
