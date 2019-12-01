package com.ryit.commons.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "商品投诉信息")
public class BusiGoodsHandlerComplaintDto implements Serializable {

    @ApiModelProperty(value = "投诉ID")
    @NotNull(message = "投诉ID不能为空")
    private Long id;

    /**
     * 处理说明
     */
    @ApiModelProperty(value = "处理说明")
    @NotBlank(message = "处理说明不能为空")
    private String handlerExplain;
}