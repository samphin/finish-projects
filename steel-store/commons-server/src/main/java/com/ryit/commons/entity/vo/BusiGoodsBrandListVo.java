package com.ryit.commons.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品分类列表Vo类
 *
 * @author samphin
 * @since 2019-10-21 17:42:47
 */
@Data
public class BusiGoodsBrandListVo implements Serializable {

    private static final long serialVersionUID = 4913771689232067865L;

    /**
     * 分类ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long brandId;

    /**
     * 类别名称
     */
    private String brandName;
}