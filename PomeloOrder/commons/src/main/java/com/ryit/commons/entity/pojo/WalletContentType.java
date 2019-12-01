package com.ryit.commons.entity.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhangweixun
 * @Date: 2019/10/9 0009下午 3:50
 * @Description: 资讯类型
 */
@Setter
@Getter
@NoArgsConstructor
public class WalletContentType implements Serializable {

    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Long id;

    /**
     * 资讯类型名称
     * isNullAble:1
     */
    private String typeName;

    /**
     * 排序字段 小到大 越大越靠后
     * isNullAble:1
     */
    private Integer sort;

    /**
     * 父类资讯类型id
     * isNullAble:1
     */
    private Long parentId;

    /**
     * 顶级分类id
     */
    private Long topParentId;

    /**
     * 0正常 2删除
     * isNullAble:1,defaultVal:0
     */
    private String delFlag;

    /**
     * 类型级别 1最高级 2次级 类推
     */
    private Integer level;

    /**
     * 是否首页展示 0否 1是
     */
    private Integer homeDisplayFlag;
}
