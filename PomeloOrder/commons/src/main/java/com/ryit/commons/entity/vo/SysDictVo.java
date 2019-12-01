package com.ryit.commons.entity.vo;

import com.ryit.commons.entity.pojo.SysDict;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : samphin
 * @Date : 2019-8-31 17:14:35
 * 系统字典
 */
@Getter
@Setter
@NoArgsConstructor
public class SysDictVo implements Serializable {

    private static final long serialVersionUID = 7581153486644586549L;

    /**
     * 字典类型描述
     */
    private String label;

    /**
     * 字典类型编码值
     */
    private String value;

    /**
     * 例如：value=house_property值添加驼峰显示houseProperty
     */
    private String valueCamelName;

    /**
     * 字典信息类型->1资质信息 2基本信息 3工作信息 4贷款信息 5其他信息
     */
    private Integer infoType;

    /**
     * 字典内容
     */
    private List<Map> content = new ArrayList<>();

}
