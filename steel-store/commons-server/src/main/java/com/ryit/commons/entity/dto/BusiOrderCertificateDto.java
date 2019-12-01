package com.ryit.commons.entity.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 上传订单支付凭证Dto
 *
 * @author samphin
 * @since 2019-10-29 18:04:27
 */
@Data
@ApiModel
public class BusiOrderCertificateDto implements Serializable {

    private static final long serialVersionUID = 7817673584047917737L;

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    /**
     * 支付凭证图片
     */
    @NotEmpty(message = "支付凭证不能为空")
    @ApiModelProperty(value = "支付凭证图片")
    private List<String> images;
}